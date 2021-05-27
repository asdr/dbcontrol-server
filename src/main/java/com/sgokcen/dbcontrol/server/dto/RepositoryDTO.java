package com.sgokcen.dbcontrol.server.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RepositoryDTO implements DataTransferObject {

    private Long pk;
    private Integer version;
    private Date createdDate;
    private Date updatedDate;
    private String uniqueId;

    private String name;
    private String status;
    private DatasourceDTO datasource;

    public RepositoryDTO() {
    }

    public RepositoryDTO(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
