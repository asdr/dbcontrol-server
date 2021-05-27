package com.sgokcen.dbcontrol.server.controller.model.res;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SetupResponseModel implements ResponseModel {
    private String uniqueId;
    private String key;
    private String value;
}
