package com.sgokcen.dbcontrol.server.admin;

import java.util.List;

import com.sgokcen.dbcontrol.server.service.AdministrationService;
import com.sgokcen.dbcontrol.server.service.RoleService;
import com.sgokcen.dbcontrol.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgokcen.dbcontrol.server.dto.RoleDTO;
import com.sgokcen.dbcontrol.server.dto.UserDTO;
import com.sgokcen.dbcontrol.server.security.DefaultSecurityRoles;
import com.sgokcen.dbcontrol.server.security.SecureResource;

@Service
public class AdministrationServiceImpl implements AdministrationService {

    private UserService userService;
    private RoleService roleService;
    
    @Autowired
    public AdministrationServiceImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    
    @SecureResource(DefaultSecurityRoles.ADMINISTRATOR)
    public void makeAdmin(UserDTO userInfo) {
        UserDTO userDTO = this.userService.getModel(userInfo);
        RoleDTO adminRole = this.roleService.getModel(new RoleDTO(DefaultSecurityRoles.ADMINISTRATOR));

        List<RoleDTO> roles = userDTO.getRoles();
        
        boolean anyMatch = roles.stream().map(r->r.getName()).anyMatch(rn -> DefaultSecurityRoles.ADMINISTRATOR.equals(rn));
        if (anyMatch == false) {
            roles.add(adminRole);
        }
        else {
            userDTO.setRoles(null); // do not make any update on the roles
        }
        
        this.userService.updateModel(userDTO);
    }

}
