package com.sgokcen.dbcontrol.server.persistence.model.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.sgokcen.dbcontrol.server.dto.RepositoryDTO;
import com.sgokcen.dbcontrol.server.exception.ResourceNotFoundException;
import com.sgokcen.dbcontrol.server.persistence.dao.DatasourceDao;
import com.sgokcen.dbcontrol.server.persistence.dao.RepositoryDao;
import com.sgokcen.dbcontrol.server.persistence.model.DatasourceEntity;
import com.sgokcen.dbcontrol.server.persistence.model.RepositoryEntity;
import com.sgokcen.dbcontrol.server.security.DefaultSecurityRoles;
import com.sgokcen.dbcontrol.server.security.SecureResource;
import com.sgokcen.dbcontrol.server.service.RandomStringService;
import com.sgokcen.dbcontrol.server.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepositoryServiceImpl extends AbstractModelServiceImpl<RepositoryEntity, RepositoryDTO> implements RepositoryService {

    private DatasourceDao datasourceDAO;
    
    @Autowired
    public RepositoryServiceImpl(RepositoryDao dao, RandomStringService random, DatasourceDao datasourceDAO) {
        super(dao, random, RepositoryEntity.class, RepositoryDTO.class);
        this.datasourceDAO = datasourceDAO;
    }
    
    @Override
    protected RepositoryEntity getEntity(RepositoryDTO info) {
        RepositoryEntity entity = super.getEntity(info);
        
        String name = info.getName();
        if (entity == null) {
            Optional<RepositoryEntity> maybeRepository = ((RepositoryDao) getDAO()).findByName(name);
            if (maybeRepository.isPresent()) {
                return maybeRepository.get();
            }
        }
        
        return entity;
    }
    
    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public void deleteModel(RepositoryDTO info) {
        super.deleteModel(info);
    }
    
    @Transactional
    public RepositoryDTO createModel(RepositoryDTO info) {
        Optional<DatasourceEntity> maybeDatasource = this.datasourceDAO.findByName(info.getDatasource().getName());
        if (maybeDatasource.isPresent() == false) {
            throw new ResourceNotFoundException();
        }
        
        DatasourceEntity datasourceEntity = maybeDatasource.get();
        return super.createModel(info, datasourceEntity);
    }
    
    

}
