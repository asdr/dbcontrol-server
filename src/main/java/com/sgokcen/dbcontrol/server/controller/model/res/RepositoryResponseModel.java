package com.sgokcen.dbcontrol.server.controller.model.res;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RepositoryResponseModel implements ResponseModel {
    private String uniqueId;
    private String name;
    private String status;
    private DatasourceResponseModel datasource;
}
