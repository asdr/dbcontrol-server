package com.sgokcen.dbcontrol.server.controller.model.req;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DatasourceRequestModel implements RequestModel {
    private String name;
    private String type;
    private String connectionString;
    private String schema;
    private String password;
    private String jndi;
}
