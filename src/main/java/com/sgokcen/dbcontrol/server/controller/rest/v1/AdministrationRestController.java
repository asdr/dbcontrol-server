package com.sgokcen.dbcontrol.server.controller.rest.v1;

import java.util.List;
import java.util.stream.Collectors;

import com.sgokcen.dbcontrol.server.controller.model.req.DatasourceRequestModel;
import com.sgokcen.dbcontrol.server.controller.model.req.SetupRequestModel;
import com.sgokcen.dbcontrol.server.controller.model.res.DatasourceResponseModel;
import com.sgokcen.dbcontrol.server.controller.model.res.SetupResponseModel;
import com.sgokcen.dbcontrol.server.dto.DatasourceDTO;
import com.sgokcen.dbcontrol.server.dto.SetupDTO;
import com.sgokcen.dbcontrol.server.dto.UserDTO;
import com.sgokcen.dbcontrol.server.service.AdministrationService;
import com.sgokcen.dbcontrol.server.service.DatasourceService;
import com.sgokcen.dbcontrol.server.service.SetupService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sgokcen.dbcontrol.server.exception.ResourceAlreadyExistsException;

@RestController
@RequestMapping(path = "/rest/v1/administration")
public class AdministrationRestController {

    private AdministrationService adminService;
    private SetupService setupService;
    private DatasourceService datasourceService;
    
    @Autowired
    public AdministrationRestController(AdministrationService adminService, SetupService setupService, DatasourceService datasourceService) {
        this.adminService = adminService;
        this.setupService = setupService;
        this.datasourceService = datasourceService;
    }
    
    @Parameters({
        @Parameter(name="x-auth-token", description="[Token Value]", in=ParameterIn.HEADER)
    })
    @PostMapping(
            path = "/users/{uniqueId}/makeAdmin")
    public ResponseEntity<?> makeAdmin(@PathVariable String uniqueId) {
        this.adminService.makeAdmin(new UserDTO(uniqueId));
        return ResponseEntity.ok().build();
    }

    @Parameters({
            @Parameter(name="x-auth-token", description="[Token Value]", in=ParameterIn.HEADER)
    })
    @PutMapping(path = "/settings")
    public ResponseEntity<SetupResponseModel> createOrUpdateSetting(@RequestBody SetupRequestModel setupRequestInfo) {
        ModelMapper modelMapper = new ModelMapper();
        SetupDTO setupInfo = modelMapper.map(setupRequestInfo, SetupDTO.class);
        SetupDTO updatedModel = null;
        try {
            updatedModel = setupService.createModel(setupInfo);
        }
        catch (ResourceAlreadyExistsException e) {
            updatedModel = setupService.updateModel(setupInfo);
        }
        return ResponseEntity.ok(modelMapper.map(updatedModel, SetupResponseModel.class));
    }

    @Parameters({
            @Parameter(name="x-auth-token", description="[Token Value]", in=ParameterIn.HEADER)
    })
    @GetMapping(path = "/settings}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<SetupResponseModel>> getSettings(int page, int limit) {
        List<SetupDTO> models = setupService.getModels(page, limit);
        ModelMapper modelMapper = new ModelMapper();
        List<SetupResponseModel> modelList = models.stream().map(info->modelMapper.map(info, SetupResponseModel.class)).collect(Collectors.toList());
        return ResponseEntity.ok(modelList);
    }

    @Parameters({
            @Parameter(name="x-auth-token", description="[Token Value]", in=ParameterIn.HEADER)
    })
    @DeleteMapping(path = "/settings/{uniqueId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> deleteSetting(@PathVariable String uniqueId) {
        SetupDTO setupInfo = new SetupDTO(uniqueId);
        this.setupService.deleteModel(setupInfo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Parameters({
            @Parameter(name="x-auth-token", description="[Token Value]", in=ParameterIn.HEADER)
    })
    @GetMapping(path = "/datasources}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<DatasourceResponseModel>> getDatasources(int page, int limit) {
        List<DatasourceDTO> models = this.datasourceService.getModels(page, limit);
        ModelMapper modelMapper = new ModelMapper();
        List<DatasourceResponseModel> modelList = models.stream().map(info->modelMapper.map(info, DatasourceResponseModel.class)).collect(Collectors.toList());
        return ResponseEntity.ok(modelList);
    }

    @Parameters({
            @Parameter(name="x-auth-token", description="[Token Value]", in=ParameterIn.HEADER)
    })
    @PutMapping(path = "/datasources", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<DatasourceResponseModel> createOrUpdateDatasource(@RequestBody DatasourceRequestModel datasourceRequest) {
        
        ModelMapper modelMapper = new ModelMapper();
        DatasourceDTO datasourceDTO = modelMapper.map(datasourceRequest, DatasourceDTO.class);
        
        DatasourceDTO updatedModel = null;
        try {
            updatedModel = this.datasourceService.createModel(datasourceDTO);
        }
        catch (ResourceAlreadyExistsException e) {
            updatedModel = this.datasourceService.updateModel(datasourceDTO);
        }
        
        return ResponseEntity.ok(new ModelMapper().map(updatedModel, DatasourceResponseModel.class));
    }

    @Parameters({
            @Parameter(name="x-auth-token", description="[Token Value]", in=ParameterIn.HEADER)
    })
    @GetMapping(path = "/datasources/{uniqueId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<DatasourceResponseModel> getDatasource(@PathVariable String uniqueId) {
        DatasourceDTO datasourceDTO = this.datasourceService.getModel(new DatasourceDTO(uniqueId));
        return ResponseEntity.ok(new ModelMapper().map(datasourceDTO, DatasourceResponseModel.class));
    }

    @Parameters({
            @Parameter(name="x-auth-token", description="[Token Value]", in=ParameterIn.HEADER)
    })
    @DeleteMapping(path = "/datasouces/{uniqueId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> deleteDatasource(@PathVariable String uniqueId) {
        DatasourceDTO info = new DatasourceDTO(uniqueId);
        this.datasourceService.deleteModel(info);
        return ResponseEntity.ok().build();
    }
    
    
}
