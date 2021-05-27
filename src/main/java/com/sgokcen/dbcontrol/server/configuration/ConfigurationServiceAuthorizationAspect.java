package com.sgokcen.dbcontrol.server.configuration;

import com.sgokcen.dbcontrol.server.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.sgokcen.dbcontrol.server.security.auth.ServiceAuthorizationAspect;

@Configuration
@EnableAspectJAutoProxy
public class ConfigurationServiceAuthorizationAspect {

    private AuthorizationService authorizationService;

    @Autowired
    public ConfigurationServiceAuthorizationAspect(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Bean
    public ServiceAuthorizationAspect authorizationAspectJ() {
        return new ServiceAuthorizationAspect(authorizationService);
    }

}
