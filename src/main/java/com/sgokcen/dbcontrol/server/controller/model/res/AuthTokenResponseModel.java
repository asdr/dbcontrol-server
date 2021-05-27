package com.sgokcen.dbcontrol.server.controller.model.res;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthTokenResponseModel implements ResponseModel {
    private String tokenValue;
    private Date expirationDate;
    private String username;
    private String ipAddress;
}
