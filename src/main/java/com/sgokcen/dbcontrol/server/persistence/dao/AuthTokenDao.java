package com.sgokcen.dbcontrol.server.persistence.dao;

import java.util.Optional;

import com.sgokcen.dbcontrol.server.persistence.model.AuthTokenEntity;

public interface AuthTokenDao extends DataAccessObject<AuthTokenEntity, Long> {
    public Optional<AuthTokenEntity> findByTokenValue(String tokenValue);
}
