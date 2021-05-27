package com.sgokcen.dbcontrol.server.persistence.dao;

import java.util.List;
import java.util.Optional;

import com.sgokcen.dbcontrol.server.persistence.model.DatasourceEntity;

public interface DatasourceDao extends DataAccessObject<DatasourceEntity, Long> {
    public Optional<DatasourceEntity> findByName(String name);
    public List<DatasourceEntity> findByType(String type);
}
