package com.sgokcen.dbcontrol.server.persistence.dao;

import java.util.Optional;

import com.sgokcen.dbcontrol.server.persistence.model.SetupEntity;

public interface SetupDao extends DataAccessObject<SetupEntity, Long> {
    public Optional<SetupEntity> findByKey(String key);
}
