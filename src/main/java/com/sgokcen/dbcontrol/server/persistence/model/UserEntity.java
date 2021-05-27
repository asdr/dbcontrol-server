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
import javax.persistence.Table;

import com.sgokcen.dbcontrol.server.security.User;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "T_USER")
@Getter @Setter
public class UserEntity extends AbstractEntity implements User {

    private String firstname;

    private String lastname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordEncrypted;

    @Column(nullable = false, columnDefinition = "VARCHAR2(1 char) DEFAULT 'N'")
    private String locked;

    @ManyToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "t_user_role",
            joinColumns = { @JoinColumn(name = "user_pk") },
            inverseJoinColumns = { @JoinColumn(name = "role_pk", foreignKey = @ForeignKey(foreignKeyDefinition = "foreign key (role_pk) references t_role on delete cascade")) })
    private List<RoleEntity> roles = new ArrayList<>();

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
        return "Y".equals(getLocked());
    }


}
