package com.sgokcen.dbcontrol.server.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "T_ROLE")
@Getter @Setter
public class RoleEntity extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String name;

}
