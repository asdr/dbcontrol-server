package com.sgokcen.dbcontrol.server.security.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.sgokcen.dbcontrol.server.domain.AuthenticationToken;
import com.sgokcen.dbcontrol.server.persistence.dao.AuthTokenDao;
import com.sgokcen.dbcontrol.server.persistence.dao.UserDao;
import com.sgokcen.dbcontrol.server.service.AuthenticationService;
import com.sgokcen.dbcontrol.server.service.RandomStringService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import com.sgokcen.dbcontrol.server.persistence.model.AuthTokenEntity;
import com.sgokcen.dbcontrol.server.persistence.model.UserEntity;
import com.sgokcen.dbcontrol.server.security.auth.AuthToken;
import lombok.Getter;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private HttpServletRequest request;
    private UserDao userDAO = null;
    private AuthTokenDao authTokenDao;
    private RandomStringService random;

    @Getter
    private int maximumTokenDurationInDays = 7;

    @Autowired
    public AuthenticationServiceImpl(HttpServletRequest request, UserDao userDAO, AuthTokenDao authTokenDao, RandomStringService random) {
        this.request = request;
        this.userDAO = userDAO;
        this.authTokenDao = authTokenDao;
        this.random = random;
    }

    public AuthTokenEntity generateAuthenticationToken(UserEntity userEntity, String ipAddress) {
        AuthTokenEntity authTokenEntity = new AuthTokenEntity();

        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_YEAR, maximumTokenDurationInDays);
        authTokenEntity.setExpirationDate(calendar.getTime());

        authTokenEntity.setIpAddress(ipAddress);
        authTokenEntity.setOwner(userEntity);
        authTokenEntity.setTokenValue(random.next(true));
        authTokenEntity.setUniqueId(random.next());

        return authTokenEntity;
    }

    @Transactional
    public AuthenticationToken login(String username, String password) {
        Optional<UserEntity> maybeUser = this.userDAO.findByEmail(username);

        if (maybeUser.isPresent()) {
            AuthTokenEntity authTokenEntity = generateAuthenticationToken(maybeUser.get(), request.getRemoteAddr());
            AuthTokenEntity savedToken = authTokenDao.save(authTokenEntity);
            return new ModelMapper().map(savedToken, AuthenticationToken.class);
        }

        throw new AuthenticationCredentialsNotFoundException("Authentication is required.");
    }

    public AuthenticationToken logout(String tokenValue) {
        Optional<AuthTokenEntity> maybeToken = this.authTokenDao.findByTokenValue(tokenValue);

        if (maybeToken.isPresent()) {
            AuthTokenEntity tokenEntity = maybeToken.get();
            tokenEntity.setValid("N");
            AuthTokenEntity savedToken = authTokenDao.save(tokenEntity);
            return new ModelMapper().map(savedToken, AuthenticationToken.class);
        }

        return null;
    }

    @Override
    public AuthToken getToken(String tokenValue) {
        Optional<AuthTokenEntity> maybeToken= this.authTokenDao.findByTokenValue(tokenValue);

        if (maybeToken.isPresent()) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DAY_OF_YEAR, maximumTokenDurationInDays);
            AuthTokenEntity authTokenEntity = maybeToken.get();
            authTokenEntity.setExpirationDate(calendar.getTime());

            AuthTokenEntity savedToken = this.authTokenDao.save(authTokenEntity);
            return new ModelMapper().map(savedToken, AuthenticationToken.class);
        }

        return null;
    }
}
