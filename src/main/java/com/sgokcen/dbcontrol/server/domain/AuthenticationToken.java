package com.sgokcen.dbcontrol.server.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

import com.sgokcen.dbcontrol.server.security.auth.AuthToken;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@SuppressWarnings("serial")
public class AuthenticationToken implements AuthToken {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationToken.class);

    private String tokenValue;
    private String ipAddress;
    private Date expirationDate;
    private String valid = "Y";
    private User owner;

    public String getUsername() {
        if (owner != null) {
            return owner.getEmail();
        }

        return null;
    }

    public boolean isValid() {
        return "Y".equals(valid);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    public Object getCredentials() {
        if (owner != null) {
            return owner.getPasswordEncrypted();
        }

        return null;
    }

    public Object getDetails() {
        // TODO Auto-generated method stub
        return null;
    }

    public Object getPrincipal() {
        return getUsername();
    }

    public boolean isAuthenticated() {
        return isValid() && this.expirationDate != null && this.expirationDate.after(new Date());
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        LOGGER.info("It is not allowed to validate an invalid authentication token.");
    }

    public String getName() {
        if (owner != null) {
            return owner.getFirstname() + " " + owner.getLastname();
        }

        return null;
    }


}
