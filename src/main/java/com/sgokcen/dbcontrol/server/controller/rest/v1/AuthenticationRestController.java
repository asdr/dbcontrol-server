package com.sgokcen.dbcontrol.server.controller.rest.v1;

import com.sgokcen.dbcontrol.server.controller.model.res.AuthTokenResponseModel;
import com.sgokcen.dbcontrol.server.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sgokcen.dbcontrol.server.security.auth.AuthToken;
import com.sgokcen.dbcontrol.server.security.auth.AuthenticationConstants;

@RestController
@RequestMapping(path = "/rest/v1/auth")
public class AuthenticationRestController {

    private AuthenticationService authService;

    @Autowired
    public AuthenticationRestController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthTokenResponseModel> login(@RequestParam String username, @RequestParam String password) {
        AuthToken token = authService.login(username, password);
        return ResponseEntity.ok(new ModelMapper().map(token, AuthTokenResponseModel.class));
    }

    @GetMapping(path = "/logout")
    public ResponseEntity<AuthTokenResponseModel> logout(@RequestHeader(name = AuthenticationConstants.AUTH_TOKEN_HEADER_KEY) String authToken) {
        AuthToken token = authService.logout(authToken);
        if (token != null) {
            return ResponseEntity.ok(new ModelMapper().map(token, AuthTokenResponseModel.class));
        }

        return ResponseEntity.notFound().build();
    }

}
