package com.sgokcen.dbcontrol.server.security;

public interface Resource {
    public String getId();
    public User getOwner();
    public String getOwnerId();
}
