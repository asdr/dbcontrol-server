package com.sgokcen.dbcontrol.server.security.auth;

import org.springframework.security.core.AuthenticationException;

public class InvalidAuthTokenException  extends AuthenticationException {

    /**
     * 
     */
    private static final long serialVersionUID = -3994484869028188792L;


    public InvalidAuthTokenException() {
        super("Invalid or missing authentication token");
    }
}
