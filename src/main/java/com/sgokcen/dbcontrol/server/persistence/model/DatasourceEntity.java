package com.sgokcen.dbcontrol.server.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "t_datasource")
@Getter @Setter
public class DatasourceEntity extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String name;
    
    @Column
    private String type;

    @Column
    private String connectionString;
    
    @Column
    private String schema;
    
    @Column
    private String passwordEncrypted;

    @Column
    private String jndi;

}
