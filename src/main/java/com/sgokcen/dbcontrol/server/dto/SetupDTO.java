package com.sgokcen.dbcontrol.server.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SetupDTO implements DataTransferObject {
    private Long pk;
    private Integer version;
    private Date createdDate;
    private Date updatedDate;
    private String uniqueId;

    private String key;
    private String value;

    public SetupDTO() {
        // TODO Auto-generated constructor stub
    }
    
    public SetupDTO(String uniqueId) {
        this.uniqueId = uniqueId;
    }
    
    public SetupDTO(String key, String value) {
        this.key = key;
        this.value = value;
    }
    
    
}
