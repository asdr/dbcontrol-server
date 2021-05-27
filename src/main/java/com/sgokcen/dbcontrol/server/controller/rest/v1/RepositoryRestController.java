package com.sgokcen.dbcontrol.server.controller.rest.v1;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.sgokcen.dbcontrol.server.controller.model.req.RepositoryRequestModel;
import com.sgokcen.dbcontrol.server.controller.model.res.RepositoryResponseModel;
import com.sgokcen.dbcontrol.server.dto.DatasourceDTO;
import com.sgokcen.dbcontrol.server.dto.RepositoryDTO;
import com.sgokcen.dbcontrol.server.service.DatasourceService;
import com.sgokcen.dbcontrol.server.service.RepositoryService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(path = "/rest/v1/repositories")
public class RepositoryRestController {

    private RepositoryService repositoryService;
    
    @Autowired
    public RepositoryRestController(RepositoryService repositoryService, DatasourceService datasourceService) {
        this.repositoryService = repositoryService;
    }
    
    @ApiImplicitParams({
        @ApiImplicitParam(name="x-auth-token", value="[Token Value]", paramType="header")
    })
    @PostMapping(
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
            )
    public ResponseEntity<RepositoryResponseModel> createRepository(@RequestBody RepositoryRequestModel repository) {
        ModelMapper modelMapper = new ModelMapper();
        
        RepositoryDTO repositoryDTO = modelMapper.map(repository, RepositoryDTO.class);
        DatasourceDTO datasourceDTO = new DatasourceDTO();
        datasourceDTO.setName(repository.getDatasourceName());
        repositoryDTO.setDatasource(datasourceDTO);
        
        RepositoryDTO createdRepository = this.repositoryService.createModel(repositoryDTO);
        
        if (createdRepository != null) {
            URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{repoId}")
                    .buildAndExpand(createdRepository.getUniqueId()).toUri();

            ResponseEntity<RepositoryResponseModel> returnValue = 
                    ResponseEntity
                    .created(location)
                    .body(modelMapper.map(createdRepository, RepositoryResponseModel.class));
            
            return returnValue;
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    @ApiImplicitParams({
        @ApiImplicitParam(name="x-auth-token", value="[Token Value]", paramType="header")
    })
    @DeleteMapping(path = "/{uniqueId}")
    public ResponseEntity<?> deleteRepository(@PathVariable String uniqueId) {
        this.repositoryService.deleteModel(new RepositoryDTO(uniqueId));
        return ResponseEntity.ok().build();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name="x-auth-token", value="[Token Value]", paramType="header")
    })
    @GetMapping(path = "/{uniqueId}",produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<RepositoryResponseModel> getRepository(@PathVariable String uniqueId) {
        RepositoryDTO repositoryDTO = this.repositoryService.getModel(new RepositoryDTO(uniqueId));
        
        ResponseEntity<RepositoryResponseModel> returnValue = 
                ResponseEntity.ok(new ModelMapper().map(repositoryDTO, RepositoryResponseModel.class));
        
        return returnValue;
    }
    
    @ApiImplicitParams({
        @ApiImplicitParam(name="x-auth-token", value="[Token Value]", paramType="header")
    })
    @GetMapping(path = "/",produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<RepositoryResponseModel>> getRepositoryList(int page, int limit) {
        List<RepositoryDTO> repositoryList = this.repositoryService.getModels(page, limit);
        
        ModelMapper modelMapper = new ModelMapper();
        ResponseEntity<List<RepositoryResponseModel>> returnValue = 
                ResponseEntity.ok(
                        repositoryList
                        .stream()
                        .map(r -> modelMapper.map(r, RepositoryResponseModel.class))
                        .collect(Collectors.toList()));
        
        return returnValue;
    }
    
}
