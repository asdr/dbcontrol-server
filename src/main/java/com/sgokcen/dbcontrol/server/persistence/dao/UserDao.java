package com.sgokcen.dbcontrol.server.persistence.dao;

import java.util.Optional;

import com.sgokcen.dbcontrol.server.persistence.model.UserEntity;

public interface UserDao extends DataAccessObject<UserEntity, Long> {

    public Optional<UserEntity> findByEmail(String email);

}
