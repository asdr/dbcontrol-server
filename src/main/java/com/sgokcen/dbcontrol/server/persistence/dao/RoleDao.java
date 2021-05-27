package com.sgokcen.dbcontrol.server.persistence.dao;

import java.util.Optional;

import com.sgokcen.dbcontrol.server.persistence.model.RoleEntity;

public interface RoleDao extends DataAccessObject<RoleEntity, Long> {
    public Optional<RoleEntity> findByName(String name);
}
