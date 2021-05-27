package com.sgokcen.dbcontrol.server.persistence.model.service;

import java.util.List;
import java.util.Optional;

import com.sgokcen.dbcontrol.server.dto.SetupDTO;
import com.sgokcen.dbcontrol.server.persistence.dao.SetupDao;
import com.sgokcen.dbcontrol.server.persistence.model.SetupEntity;
import com.sgokcen.dbcontrol.server.security.DefaultSecurityRoles;
import com.sgokcen.dbcontrol.server.security.SecureResource;
import com.sgokcen.dbcontrol.server.service.RandomStringService;
import com.sgokcen.dbcontrol.server.service.SetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetupServiceImpl extends AbstractModelServiceImpl<SetupEntity, SetupDTO> implements SetupService {

    @Autowired
    public SetupServiceImpl(SetupDao setupDAO, RandomStringService random) {
        super(setupDAO, random, SetupEntity.class, SetupDTO.class);
    }

    @Override
    protected SetupEntity getEntity(SetupDTO setupInfo) {
        SetupEntity setupEntity = super.getEntity(setupInfo);

        String key = setupInfo.getKey();
        if (setupEntity == null && key != null) {
            Optional<SetupEntity> maybeSetup = ((SetupDao) getDAO()).findByKey(key);
            if (maybeSetup.isPresent()) {
                setupEntity = maybeSetup.get();
            }
        }
        return setupEntity;
    }
    
    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public SetupDTO createModel(SetupDTO info) {
        return super.createModel(info);
    }
    
    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public void deleteModel(SetupDTO info) {
        super.deleteModel(info);
    }
    
    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public SetupDTO getModel(SetupDTO info) {
        return super.getModel(info);
    }
    
    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public List<SetupDTO> getModels(int page, int limit) {
        return super.getModels(page, limit);
    }
    
    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public SetupDTO updateModel(SetupDTO info) {
        return super.updateModel(info);
    }

}
