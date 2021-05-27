package com.sgokcen.dbcontrol.server.controller.model.res;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RoleResponseModel implements ResponseModel {
    private String uniqueId;
    private String name;
}
