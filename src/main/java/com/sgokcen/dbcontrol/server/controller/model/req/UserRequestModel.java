package com.sgokcen.dbcontrol.server.controller.model.req;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRequestModel implements RequestModel {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
