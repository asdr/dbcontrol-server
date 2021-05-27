package com.sgokcen.dbcontrol.server.security.auth;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgokcen.dbcontrol.server.domain.AuthenticationToken;
import com.sgokcen.dbcontrol.server.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;

public class AuthTokenAuthenticationFilter extends AuthenticationFilter implements AuthenticationSuccessHandler, AuthenticationFailureHandler  {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenAuthenticationFilter.class);

    private String contextPath = null;

    private List<SimpleEntry<String, String>> allowsURIs = new ArrayList<SimpleEntry<String, String>>();
    private List<SimpleEntry<String, String>> restrictedURIs = new ArrayList<SimpleEntry<String,String>>();

    private HandlerExceptionResolver resolver;

    public AuthTokenAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationService authenticationService) {
        super(authenticationManager, new AuthTokenAuthenticationConverter(authenticationService));

        setRequestMatcher(request -> {
            final String requestURI = request.getRequestURI().substring(contextPath.length());
            RequestURIMatcher uriMatcher = new RequestURIMatcher(requestURI);

            boolean allowed = false;

            for (SimpleEntry<String, String> rule : allowsURIs) {
                if ("ANY".equals(rule.getKey()) || request.getMethod().equals(rule.getKey())) {
                    if (uriMatcher.test(rule.getValue())) {
                        allowed = true;
                        break;
                    }
                }
            }

            boolean restricted = false;

            for (SimpleEntry<String, String> rule : restrictedURIs) {
                if ("ANY".equals(rule.getKey()) || request.getMethod().equals(rule.getKey())) {
                    if (uriMatcher.test(rule.getValue())) {
                        restricted = true;
                        break;
                    }
                }
            }

            if (allowed)
                return !allowed;

            return restricted;
        });

        setSuccessHandler(this);
        setFailureHandler(this);
    }

    public AuthTokenAuthenticationFilter setResolver(HandlerExceptionResolver resolver) {
        this.resolver = resolver;
        return this;

    }

    public AuthTokenAuthenticationFilter setContextPath(String contextPath) {
        this.contextPath = contextPath;
        return this;
    }

    public AuthTokenAuthenticationFilter allow(String method, String uri) {
        this.allowsURIs.add(new SimpleEntry<String, String>(method, uri));
        return this;
    }

    public AuthTokenAuthenticationFilter allow(String... uri) {
        if (uri.length > 0) {
            Arrays.stream(uri).forEach(u -> this.allowsURIs.add(new SimpleEntry<String, String>("ANY", u)));
        }
        return this;
    }

    public AuthTokenAuthenticationFilter deny(String method, String uri) {
        this.restrictedURIs.add(new SimpleEntry<String, String>(method, uri));
        return this;
    }

    public AuthTokenAuthenticationFilter deny(String... uri) {
        if (uri.length > 0) {
            Arrays.stream(uri).forEach(u -> this.restrictedURIs.add(new SimpleEntry<String, String>("ANY", u)));
        }
        return this;
    }


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        LOGGER.info("Authentication Failure");

        resolver.resolveException(request, response, null, exception);
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        LOGGER.info("Authentication success");
    }

    static final class RequestURIMatcher implements Predicate<String> {

        private String value;

        public RequestURIMatcher(String value) {
            this.value = value;
        }

        @Override
        public boolean test(String t) {
            String normalizedPattern = t.replaceAll(Pattern.quote("**"), ".*");
            normalizedPattern = normalizedPattern.replaceAll("[^.]"+Pattern.quote("*"), "[^/]*");
            Pattern p = Pattern.compile(normalizedPattern);
            Matcher matcher = p.matcher(value);
            //			LOGGER.info("Matches: {}, Pattern: {} Against: {}", matcher.matches(), normalizedPattern, request.getRequestURI());
            return matcher.matches();
        }

    }

    public static class AuthTokenAuthenticationConverter implements AuthenticationConverter {

        private AuthenticationService authenticationService = null;

        public AuthTokenAuthenticationConverter(AuthenticationService authenticationService) {
            this.authenticationService = authenticationService;
        }

        @Override
        public Authentication convert(HttpServletRequest request) {
            LOGGER.info("attempting authentication ...");

            String tokenValue = request.getHeader(AuthenticationConstants.AUTH_TOKEN_HEADER_KEY);

            if (tokenValue != null) {
                return authenticationService.getToken(tokenValue);
            }

            return new AuthenticationToken();
        }

    }



}
