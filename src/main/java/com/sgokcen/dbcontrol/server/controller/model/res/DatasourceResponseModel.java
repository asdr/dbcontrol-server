package com.sgokcen.dbcontrol.server.controller.model.res;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DatasourceResponseModel implements ResponseModel {
    private String uniqueId;
    private String name;
    private String type;
    private String connectionString;
    private String schema;
    private String jndi;
}
