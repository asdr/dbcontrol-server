package com.sgokcen.dbcontrol.server.service;

public interface AuthorizationService {
    public void authorize(String resourceName, String[] securityRoles);
}
