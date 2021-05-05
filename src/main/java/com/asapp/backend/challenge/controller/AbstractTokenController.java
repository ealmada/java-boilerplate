package com.asapp.backend.challenge.controller;

import com.asapp.backend.challenge.resources.LoginResource;
import com.asapp.backend.challenge.security.TokenService;
import spark.Request;

public abstract class AbstractTokenController {

    private static final String TOKEN_PREFIX = "Bearer";

    private final TokenService tokenService;

    public AbstractTokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    protected LoginResource getUserPrincipal(Request request) {
        String authorizationHeader = request.headers("Authorization");
        String token = authorizationHeader.replace(TOKEN_PREFIX, "");
        return tokenService.getUserPrincipal(token);
    }

    protected String getUserNameFromToken(Request request) {
        String authorizationHeader = request.headers("Authorization");
        String token = authorizationHeader.replace(TOKEN_PREFIX, "");
        return tokenService.getUserPrincipal(token).getUsername();
    }

}
