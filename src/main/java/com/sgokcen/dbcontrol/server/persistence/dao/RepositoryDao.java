package com.sgokcen.dbcontrol.server.persistence.dao;

import java.util.Optional;

import com.sgokcen.dbcontrol.server.persistence.model.DatasourceEntity;
import com.sgokcen.dbcontrol.server.persistence.model.RepositoryEntity;

public interface RepositoryDao extends DataAccessObject<RepositoryEntity, Long> {
    public Optional<RepositoryEntity> findByName(String name);
    public Optional<RepositoryEntity> findByDatasource(DatasourceEntity datasourceEntity);
}
