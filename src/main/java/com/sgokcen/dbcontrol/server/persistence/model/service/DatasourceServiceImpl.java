package com.sgokcen.dbcontrol.server.persistence.model.service;

import java.util.List;
import java.util.Optional;

import com.sgokcen.dbcontrol.server.dto.DatasourceDTO;
import com.sgokcen.dbcontrol.server.persistence.dao.DataAccessObject;
import com.sgokcen.dbcontrol.server.persistence.dao.DatasourceDao;
import com.sgokcen.dbcontrol.server.persistence.model.DatasourceEntity;
import com.sgokcen.dbcontrol.server.security.DefaultSecurityRoles;
import com.sgokcen.dbcontrol.server.security.SecureResource;
import com.sgokcen.dbcontrol.server.service.DatasourceService;
import com.sgokcen.dbcontrol.server.service.RandomStringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatasourceServiceImpl extends AbstractModelServiceImpl<DatasourceEntity, DatasourceDTO> implements DatasourceService {

    @Autowired
    public DatasourceServiceImpl(DataAccessObject<DatasourceEntity, Long> dao, RandomStringService random) {
        super(dao, random, DatasourceEntity.class, DatasourceDTO.class);
    }
    
    @Override
    protected DatasourceEntity getEntity(DatasourceDTO info) {
        DatasourceEntity entity = super.getEntity(info);
        
        String name = info.getName();
        if (entity == null) {
            Optional<DatasourceEntity> maybeDatasource = ((DatasourceDao) getDAO()).findByName(name);
            if (maybeDatasource.isPresent()) {
                return maybeDatasource.get();
            }
        }
        
        return entity;
    }
    
    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public DatasourceDTO createModel(DatasourceDTO info) {
        return super.createModel(info);
    }
    
    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public DatasourceDTO getModel(DatasourceDTO info) {
        return super.getModel(info);
    }
    
    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public List<DatasourceDTO> getModels(int page, int limit) {
        return super.getModels(page, limit);
    }
    
    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public DatasourceDTO updateModel(DatasourceDTO info) {
        return super.updateModel(info);
    }
    
    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public void deleteModel(DatasourceDTO info) {
        super.deleteModel(info);
    }

}
