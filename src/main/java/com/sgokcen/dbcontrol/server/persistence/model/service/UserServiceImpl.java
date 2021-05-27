package com.sgokcen.dbcontrol.server.persistence.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sgokcen.dbcontrol.server.dto.UserDTO;
import com.sgokcen.dbcontrol.server.persistence.dao.UserDao;
import com.sgokcen.dbcontrol.server.persistence.model.UserEntity;
import com.sgokcen.dbcontrol.server.security.DefaultSecurityRoles;
import com.sgokcen.dbcontrol.server.security.SecureResource;
import com.sgokcen.dbcontrol.server.service.PasswordEncoderService;
import com.sgokcen.dbcontrol.server.service.RandomStringService;
import com.sgokcen.dbcontrol.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractModelServiceImpl<UserEntity, UserDTO> implements UserService {

    private PasswordEncoderService passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RandomStringService random, PasswordEncoderService passwordEncoder) {
        super(userDao, random, UserEntity.class, UserDTO.class);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected UserEntity getEntity(UserDTO userInfo) {
        UserEntity userEntity = super.getEntity(userInfo);

        String email = userInfo.getEmail();
        if (userEntity == null && email != null) {
            Optional<UserEntity> maybeUser = ((UserDao) getDAO()).findByEmail(email);
            if (maybeUser.isPresent()) {
                userEntity = maybeUser.get();
            }
        }
        return userEntity;
    }

    public UserDTO createModel(UserDTO userInfo) {
        userInfo.setPasswordEncrypted(passwordEncoder.encode(userInfo.getPassword()));
        return super.createModel(userInfo);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> maybeUser = ((UserDao) getDAO()).findByEmail(username);
        if (maybeUser.isPresent() == false) {
            throw new UsernameNotFoundException(username);
        }
        UserEntity userEntity = maybeUser.get();
        return new User(userEntity.getEmail(), userEntity.getPasswordEncrypted(), new ArrayList<>());
    }

    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public List<UserDTO> getModels(int page, int limit) {
        return super.getModels(page, limit);
    }

    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public UserDTO getModel(UserDTO info) {
        return super.getModel(info);
    }

    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public UserDTO updateModel(UserDTO info) {
        return super.updateModel(info);
    }

    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public void deleteModel(UserDTO info) {
        super.deleteModel(info);
    }

}
