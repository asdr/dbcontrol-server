package com.sgokcen.dbcontrol.server.persistence.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "t_repository")
@Getter @Setter
public class RepositoryEntity extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String name;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DatasourceEntity datasource;
    
    @Column
    private String status;
    
}
