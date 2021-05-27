package com.sgokcen.dbcontrol.server.controller.rest.v1;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.sgokcen.dbcontrol.server.controller.model.req.UserRequestModel;
import com.sgokcen.dbcontrol.server.controller.model.res.UserResponseModel;
import com.sgokcen.dbcontrol.server.dto.UserDTO;
import com.sgokcen.dbcontrol.server.service.UserService;
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
@RequestMapping(path = "/rest/v1/users")
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name="x-auth-token", value="[Token Value]", paramType="header")
    })
    @GetMapping(
            path = "/{userId}", 
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
            )
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String uniqueId) {
        UserDTO userInfo = new UserDTO();
        userInfo.setUniqueId(uniqueId);

        UserDTO user = this.userService.getModel(userInfo);
        if (user != null) {
            UserResponseModel response = new ModelMapper().map(user, UserResponseModel.class);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
            )
    public ResponseEntity<UserResponseModel> createUser(@RequestBody UserRequestModel userDetails) {
        ModelMapper mapper = new ModelMapper();

        UserDTO createdUser = userService.createModel(mapper.map(userDetails, UserDTO.class));

        if (createdUser != null) {
            URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{userId}")
                    .buildAndExpand(createdUser.getUniqueId()).toUri();

            ResponseEntity<UserResponseModel> returnValue = ResponseEntity.created(location).body(mapper.map(createdUser, UserResponseModel.class));
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
    public List<UserResponseModel> listUsers(@RequestParam(value="page", defaultValue="1") int page, 
            @RequestParam(value="limit", defaultValue="25") int limit) {
        List<UserResponseModel> userList = new ArrayList<>();
        List<UserDTO> users = userService.getModels(page, limit);

        ModelMapper mapper = new ModelMapper();
        users.forEach(user -> {
            userList.add(mapper.map(user, UserResponseModel.class));
        });

        return userList;
    }
    
    @ApiImplicitParams({
        @ApiImplicitParam(name="x-auth-token", value="<token value>", paramType="header")
    })
    @DeleteMapping(path = "/{uniqueId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> deleteUser(@PathVariable String uniqueId) {
        UserDTO userInfo = new UserDTO();
        userInfo.setUniqueId(uniqueId);
        userService.deleteModel(userInfo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
