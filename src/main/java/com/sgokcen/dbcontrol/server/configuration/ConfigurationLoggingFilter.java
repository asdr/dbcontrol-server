package com.sgokcen.dbcontrol.server.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class ConfigurationLoggingFilter extends OncePerRequestFilter {

//    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

//        LOGGER.info("Logging Request  {} : {}", request.getMethod(), request.getRequestURI());

        //call next filter in the filter chain
        filterChain.doFilter(request, response);

//        LOGGER.info("Logging Response :{}", response.getContentType());		
    }

}
