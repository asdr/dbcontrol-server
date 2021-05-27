package com.sgokcen.dbcontrol.server.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RoleDTO implements DataTransferObject {

    private Long pk;
    private Integer version;
    private Date createdDate;
    private Date updatedDate;
    private String uniqueId;

    private String name;
    
    public RoleDTO() {
    }
    
    public RoleDTO(String name) {
        this.name = name;
    }
    
}