package com.sgokcen.dbcontrol.server.security.auth;

import com.sgokcen.dbcontrol.server.domain.AuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication;
        }

        throw new InvalidAuthTokenException();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == AuthenticationToken.class;
    }

}
