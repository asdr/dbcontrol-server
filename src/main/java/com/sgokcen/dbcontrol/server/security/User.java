package com.sgokcen.dbcontrol.server.security;


public interface User extends Resource {
    public String getUsername();
    public boolean isValid();
}
