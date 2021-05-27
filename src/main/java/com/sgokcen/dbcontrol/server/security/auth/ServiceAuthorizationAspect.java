package com.sgokcen.dbcontrol.server.security.auth;

import java.lang.reflect.Method;

import com.sgokcen.dbcontrol.server.security.SecureResource;
import com.sgokcen.dbcontrol.server.service.AuthorizationService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class ServiceAuthorizationAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAuthorizationAspect.class);

    private AuthorizationService authorizationService;

    public ServiceAuthorizationAspect(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Pointcut("execution(* com.sgokcen.dbcontrol.server.service.*+.*(..))")
    private void allServices() {}

    @Before("allServices()")
    public void beforeServiceCallAuthorization(JoinPoint jp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        String methodName = methodSignature.getName();
        Method method  = methodSignature.getMethod();

        SecureResource secureResource = method.getDeclaredAnnotation(SecureResource.class);
        if (secureResource != null) {
            String resourceName = jp.getTarget().getClass().getSimpleName() + "." + methodName;
            LOGGER.info("Secure method: {}", resourceName);

            String[] resourceRoles = null;
            
            if (secureResource.value() != null && secureResource.value().trim().length() > 0) {
                resourceRoles = new String[] { secureResource.value() };
            }
            else {
                resourceRoles = secureResource.roles();
            }
            
            authorizationService.authorize(resourceName, resourceRoles);
        }
    }
}
