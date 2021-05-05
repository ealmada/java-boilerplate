package com.asapp.backend.challenge.filter;

import com.asapp.backend.challenge.security.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spark.Filter;
import spark.Request;
import spark.Response;

import java.util.logging.Logger;

import static spark.Spark.halt;

@Slf4j
@Component
public class TokenValidatorFilter {
    private static final Logger LOG = Logger.getLogger(TokenValidatorFilter.class.getName());

    private static final String TOKEN_PREFIX = "Bearer";
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String REGISTRATION_ENDPOINT = "/create";
    private static final String HTTP_POST = "POST";

    @Value("authEndpointPrefix")
    private static String authEndpointPrefix;

    private static TokenService tokenService;

    public static Filter validateUser = (Request req, Response resp) -> {
        if (!isLoginRequest(req) && !isRegistrationRequest(req)) {
            String authorizationHeader = req.headers("Authorization");
            if (authorizationHeader == null) {
                LOG.warning("Missing Authorization header");
                halt(401);
            } else if (!tokenService.validateToken(authorizationHeader.replace(TOKEN_PREFIX, ""))) {
                LOG.warning("Expired token " + authorizationHeader);
                halt(401);
            }
        }
    };

    @Autowired
    public TokenValidatorFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    private static boolean isLoginRequest(Request request) {
        return request.uri().equals(authEndpointPrefix + LOGIN_ENDPOINT) && request.requestMethod().equals(HTTP_POST);
    }

    private static boolean isRegistrationRequest(Request request) {
        return request.uri().equals(authEndpointPrefix + REGISTRATION_ENDPOINT) && request.requestMethod().equals(HTTP_POST);
    }

    public void handle(Request request, Response response) {
        if (!isLoginRequest(request) && !isRegistrationRequest(request)) {
            String authorizationHeader = request.headers("Authorization");
            if (authorizationHeader == null) {
                LOG.warning("Missing Authorization header");
                halt(401);
            } else if (!tokenService.validateToken(authorizationHeader.replace(TOKEN_PREFIX, ""))) {
                LOG.warning("Expired token " + authorizationHeader);
                halt(401);
            }
        }
    }
}
