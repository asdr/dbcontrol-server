package com.sgokcen.dbcontrol.server.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "t_setup")
@Getter @Setter
public class SetupEntity extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String key;

    private String value;
}
