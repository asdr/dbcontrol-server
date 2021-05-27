package com.sgokcen.dbcontrol.server.service;

import com.sgokcen.dbcontrol.server.security.auth.AuthToken;

public interface AuthenticationService {
    public int getMaximumTokenDurationInDays();
    public AuthToken login(String username, String password);
    public AuthToken logout(String tokenValue);
    public AuthToken getToken(String tokenValue);
}
