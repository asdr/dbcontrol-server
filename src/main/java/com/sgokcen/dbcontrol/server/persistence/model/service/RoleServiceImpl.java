package com.sgokcen.dbcontrol.server.persistence.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgokcen.dbcontrol.server.dto.RoleDTO;
import com.sgokcen.dbcontrol.server.persistence.dao.RoleDao;
import com.sgokcen.dbcontrol.server.persistence.model.RoleEntity;
import com.sgokcen.dbcontrol.server.security.DefaultSecurityRoles;
import com.sgokcen.dbcontrol.server.security.SecureResource;
import com.sgokcen.dbcontrol.server.service.RandomStringService;
import com.sgokcen.dbcontrol.server.service.RoleService;

@Service
public class RoleServiceImpl extends AbstractModelServiceImpl<RoleEntity, RoleDTO> implements RoleService {

    @Autowired
    public RoleServiceImpl(RoleDao roleDAO, RandomStringService random) {
        super(roleDAO, random, RoleEntity.class, RoleDTO.class);
    }

    @Override
    protected RoleEntity getEntity(RoleDTO roleInfo) {
        RoleEntity roleEntity = super.getEntity(roleInfo);

        String name = roleInfo.getName();
        if (roleEntity == null && name != null) {
            Optional<RoleEntity> maybeRole = ((RoleDao) getDAO()).findByName(name);
            if (maybeRole.isPresent()) {
                roleEntity = maybeRole.get();
            }
        }
        return roleEntity;
    }
    
    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public RoleDTO createModel(RoleDTO info) {
        return super.createModel(info);
    }
    
    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public void deleteModel(RoleDTO info) {
        super.deleteModel(info);
    }
    
    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public RoleDTO getModel(RoleDTO info) {
        return super.getModel(info);
    }
    
    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public List<RoleDTO> getModels(int page, int limit) {
        return super.getModels(page, limit);
    }
    
    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public RoleDTO updateModel(RoleDTO info) {
        return super.updateModel(info);
    }
}
