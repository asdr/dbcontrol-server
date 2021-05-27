package com.sgokcen.dbcontrol.server.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Role implements com.sgokcen.dbcontrol.server.security.Role {
    private String name;
}
