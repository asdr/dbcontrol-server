package com.sgokcen.dbcontrol.server.persistence.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity @Table(name = "t_auth_token")
@Getter @Setter
@ToString @EqualsAndHashCode(callSuper = false)
public class AuthTokenEntity extends AbstractEntity {

    @Column(unique = true)
    private String tokenValue;

    private String ipAddress;

    @Column(nullable = false)
    private Date expirationDate;

    @Column(columnDefinition = "VARCHAR2(1 char) DEFAULT 'Y'")
    private String valid = "Y";

    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity owner;
}
