package com.sgokcen.dbcontrol.server.persistence.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sgokcen.dbcontrol.server.persistence.model.AbstractEntity;

@NoRepositoryBean
public interface DataAccessObject<E extends AbstractEntity, ID> extends JpaRepository<E, ID> {
    public Optional<E> findByUniqueId(String uniqueId);
}
