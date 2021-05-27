package com.sgokcen.dbcontrol.server.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "t_resource")
@Getter @Setter
public class ResourceEntity extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity owner;

    @ManyToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "t_resource_role",
            joinColumns = { @JoinColumn(name = "resource_pk") },
            inverseJoinColumns = { @JoinColumn(name = "role_pk", foreignKey = @ForeignKey(foreignKeyDefinition = "foreign key (role_pk) references t_role on delete cascade")) })
    private List<RoleEntity> roles = new ArrayList<>();

}
