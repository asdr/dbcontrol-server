package com.sgokcen.dbcontrol.server.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DatasourceDTO implements DataTransferObject {

    private Long pk;
    private Integer version;
    private Date createdDate;
    private Date updatedDate;
    private String uniqueId;

    private String name;
    private String type;
    private String connectionString;
    private String schema;
    private String passwordEncrypted;
    private String jndi;

    private String password;

    public DatasourceDTO() {
    }

    public DatasourceDTO(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
