package com.sgokcen.dbcontrol.server.controller.model.res;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponseModel implements ResponseModel {
    private String uniqueId;
    private String firstname;
    private String lastname;
    private String email;
    private String locked;
}
