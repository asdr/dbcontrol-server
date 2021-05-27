
package com.sgokcen.dbcontrol.server.controller.rest.v1;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.sgokcen.dbcontrol.server.controller.model.req.RoleRequestModel;
import com.sgokcen.dbcontrol.server.controller.model.res.RoleResponseModel;
import com.sgokcen.dbcontrol.server.dto.RoleDTO;
import com.sgokcen.dbcontrol.server.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(path = "/rest/v1/roles")
public class RoleRestController {

    private RoleService roleService;

    @Autowired
    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
            )
    public ResponseEntity<RoleResponseModel> createRole(@RequestBody RoleRequestModel roleDetails) {
        ModelMapper mapper = new ModelMapper();

        RoleDTO createdRole = roleService.createModel(mapper.map(roleDetails, RoleDTO.class));

        if (createdRole != null) {
            URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{roleId}")
                    .buildAndExpand(createdRole.getUniqueId()).toUri();

            ResponseEntity<RoleResponseModel> returnValue = ResponseEntity.created(location).body(mapper.map(createdRole, RoleResponseModel.class));
            return returnValue;
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name="x-auth-token", value="<token value>", paramType="header")
    })
    @GetMapping(
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
            )
    public List<RoleResponseModel> listRoles(@RequestParam(value="page", defaultValue="1") int page, @RequestParam(value="limit", defaultValue="25") int limit) {
        List<RoleResponseModel> roleList = new ArrayList<>();
        List<RoleDTO> roles = roleService.getModels(page, limit);

        ModelMapper mapper = new ModelMapper();
        roles.forEach(user -> {
            roleList.add(mapper.map(user, RoleResponseModel.class));
        });

        return roleList;
    }
    
    @ApiImplicitParams({
        @ApiImplicitParam(name="x-auth-token", value="<token value>", paramType="header")
    })
    @DeleteMapping(path = "/{name}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> deleteRole(@PathVariable String name) {
        RoleDTO roleInfo = new RoleDTO();
        roleInfo.setName(name);
        roleService.deleteModel(roleInfo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}