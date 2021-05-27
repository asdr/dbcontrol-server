package com.sgokcen.dbcontrol.server.security.auth;

public class AuthenticationConstants {
    public static final String AUTH_TOKEN_HEADER_KEY = "X-AUTH-TOKEN";

    public static final String LOGIN_API = "/rest/v1/auth/login";
    public static final String LOGOUT_API = "/rest/v1/auth/logout";
    public static final String LOGIN_PAGE = "/ui/login";
    public static final String LOGOUT_PAGE = "/ui/logout";
    public static final String SIGNUP_API = "/rest/v1/users";
    public static final String SIGNUP_PAGE = "/ui/signup";

    public static final String[] RESTRICTED_ENDPOINT = { "/rest/**" };
}
