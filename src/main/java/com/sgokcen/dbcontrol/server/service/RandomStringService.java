package com.sgokcen.dbcontrol.server.service;

public interface RandomStringService {
    public String next();
    public String next(boolean withTimestamp);
}
