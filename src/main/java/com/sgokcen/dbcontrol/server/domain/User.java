package com.sgokcen.dbcontrol.server.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User implements com.sgokcen.dbcontrol.server.security.User {

    private String firstname;
    private String lastname;
    private String email;
    private String locked = "N";
    private String passwordEncrypted;
    private List<Role> roles;

    public String getId() {
        return getEmail();
    }

    public String getUsername() {
        return getEmail();
    }

    public boolean isValid() {
        return "Y".equals(getLocked()) == false;
    }

    public User getOwner() {
        return this;
    }

    @Override
    public String getOwnerId() {
        return getEmail();
    }
}
