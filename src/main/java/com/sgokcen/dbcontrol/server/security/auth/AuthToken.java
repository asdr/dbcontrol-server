package com.sgokcen.dbcontrol.server.security.auth;

import java.util.Date;

import org.springframework.security.core.Authentication;

public interface AuthToken extends Authentication {
    public String getTokenValue();
    public String getIpAddress();
    public Date getExpirationDate();
    public String getUsername();
    public boolean isValid();
}
