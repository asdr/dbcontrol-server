package com.sgokcen.dbcontrol.server.dto;

import java.util.Date;
import java.util.List;

import com.sgokcen.dbcontrol.server.security.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO implements DataTransferObject, User {

    private Long pk;
    private Integer version;
    private Date createdDate;
    private Date updatedDate;
    private String uniqueId;

    private String firstname;
    private String lastname;
    private String email;
    private String locked = "N";
    private String passwordEncrypted;
    private List<RoleDTO> roles;

    private String password;

    public UserDTO() {
        // TODO Auto-generated constructor stub
    }

    public UserDTO(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public String getId() {
        return getEmail();
    }

    @Override
    public User getOwner() {
        return this;
    }

    @Override
    public String getOwnerId() {
        return getEmail();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isValid() {
        return "Y".equals(getLocked()) == false;
    }

}
