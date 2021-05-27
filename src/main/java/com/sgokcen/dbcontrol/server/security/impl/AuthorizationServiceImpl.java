package com.sgokcen.dbcontrol.server.security.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.transaction.Transactional;

import com.sgokcen.dbcontrol.server.domain.AuthenticationToken;
import com.sgokcen.dbcontrol.server.domain.Role;
import com.sgokcen.dbcontrol.server.persistence.dao.ResourceDao;
import com.sgokcen.dbcontrol.server.service.AuthorizationService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sgokcen.dbcontrol.server.persistence.model.ResourceEntity;
import com.sgokcen.dbcontrol.server.persistence.model.RoleEntity;
import com.sgokcen.dbcontrol.server.persistence.model.UserEntity;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private ResourceDao resourceDAO;

    public AuthorizationServiceImpl(ResourceDao resourceDAO) {
        this.resourceDAO = resourceDAO;
    }

    @Transactional
    public void authorize(String resourceName, String[] securityRoles) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication == null || authentication.isAuthenticated() == false) {
            throw new AuthenticationCredentialsNotFoundException("Authentication is required.");
        }

        AuthenticationToken authToken = (AuthenticationToken) authentication;
        List<Role> roles = null;

        if (securityRoles.length > 0) {
            roles = authToken.getOwner().getRoles();

            if (roles == null || roles.size() == 0) {
                throw new AccessDeniedException("Access is denied.");
            }

            boolean allMatch = Arrays.asList(securityRoles).stream().allMatch(new RoleCheckPredicate(roles));

            if (allMatch == false) {
                throw new AccessDeniedException("Access is denied.");
            }
        }

        Optional<ResourceEntity> maybeResource = resourceDAO.findByName(resourceName);
        if (maybeResource.isPresent()) {
            ResourceEntity resourceEntity = maybeResource.get();

            List<RoleEntity> requiredRoles = resourceEntity.getRoles();
            if (requiredRoles != null && requiredRoles.size() > 0) {

                UserEntity owner = resourceEntity.getOwner();

                if (authentication.getPrincipal().equals(owner.getEmail()) == false) { //allow owners to access
                    if (roles == null) {
                        roles = authToken.getOwner().getRoles();
                    }

                    if (roles == null || roles.size() == 0) {
                        throw new AccessDeniedException("Access is denied.");
                    }

                    boolean allMatch = requiredRoles.stream().map(rr->rr.getName()).allMatch(new RoleCheckPredicate(roles));

                    if (allMatch == false) {
                        throw new AccessDeniedException("Access is denied.");
                    }
                }
            }
        }
    }

    private static final class RoleCheckPredicate implements Predicate<String> {

        private List<Role> givenRoles;

        public RoleCheckPredicate(List<Role> givenRoles) {
            this.givenRoles = givenRoles;
        }

        @Override
        public boolean test(String rr) {
            for (Role gr : givenRoles) {
                if (rr.equals(gr.getName())) {
                    return true;
                }
            }
            return false;
        }

    }

}
