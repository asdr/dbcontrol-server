package com.sgokcen.dbcontrol.server.configuration;

import com.sgokcen.dbcontrol.server.service.AuthenticationService;
import com.sgokcen.dbcontrol.server.service.PasswordEncoderService;
import com.sgokcen.dbcontrol.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.sgokcen.dbcontrol.server.security.auth.AuthenticationConstants;
import com.sgokcen.dbcontrol.server.security.auth.AuthTokenAuthenticationFilter;
import com.sgokcen.dbcontrol.server.security.auth.AuthTokenAuthenticationProvider;

@EnableWebSecurity
public class ConfigurationWebSecurity extends WebSecurityConfigurerAdapter {

    private AuthenticationService authenticationService;
    private UserService userDetailsService;
    private PasswordEncoderService passwordEncoderService;
    private AuthTokenAuthenticationProvider authProvider;
    private HandlerExceptionResolver resolver;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    public ConfigurationWebSecurity(AuthenticationService authenticationService, UserService userDetailsService, PasswordEncoderService passwordEncoderService, AuthTokenAuthenticationProvider authProvider, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoderService = passwordEncoderService;
        this.authProvider = authProvider;
        this.resolver = resolver;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .cors().and()
        .csrf().disable()
        //			.exceptionHandling().disable()
//        			.logout()
//        				.logoutUrl(AuthenticationConstants.LOGOUT_API).and()
//        			.formLogin()
//        				.loginPage(AuthenticationConstants.LOGIN_PAGE)
        //				.loginProcessingUrl(AuthenticationConstants.LOGIN_API)
//        				.and()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, AuthenticationConstants.SIGNUP_API).permitAll()
        .antMatchers(HttpMethod.POST, AuthenticationConstants.LOGIN_API).permitAll()
        .antMatchers(AuthenticationConstants.RESTRICTED_ENDPOINT).authenticated()
        .anyRequest().permitAll()
        .and()
        .addFilterBefore(new ConfigurationLoggingFilter(), ChannelProcessingFilter.class)
        .addFilterAfter(getAuthenticationFilter(), BasicAuthenticationFilter.class)
        //		.addFilter(new AuthorizationFilter(authenticationManager(), securityProperties, userDetailsService))
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider).userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoderService);
    }

    private AuthTokenAuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthTokenAuthenticationFilter filter = new AuthTokenAuthenticationFilter(authenticationManager(), authenticationService)
                .setResolver(resolver)
                .setContextPath(contextPath)
                .allow(HttpMethod.POST.name(), AuthenticationConstants.SIGNUP_API)
                .allow(HttpMethod.POST.name(), AuthenticationConstants.LOGIN_API)
                .deny(AuthenticationConstants.RESTRICTED_ENDPOINT);
        return filter;

    }

}
