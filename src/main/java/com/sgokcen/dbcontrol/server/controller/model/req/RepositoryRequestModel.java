package com.sgokcen.dbcontrol.server.controller.model.req;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RepositoryRequestModel implements RequestModel {
    private String name;
    private String datasourceName;
}
