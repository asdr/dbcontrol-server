package com.sgokcen.dbcontrol.server.persistence.dao;

import java.util.Optional;

import com.sgokcen.dbcontrol.server.persistence.model.ResourceEntity;

public interface ResourceDao extends DataAccessObject<ResourceEntity, Long> {
    public Optional<ResourceEntity> findByName(String name);
}
