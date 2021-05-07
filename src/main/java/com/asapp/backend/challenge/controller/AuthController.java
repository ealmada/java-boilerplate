package com.asapp.backend.challenge.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.asapp.backend.challenge.resources.UserResource;
import com.asapp.backend.challenge.security.TokenService;
import com.asapp.backend.challenge.service.UserService;
import com.asapp.backend.challenge.utils.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class AuthController extends AbstractTokenController {

    private static final String TOKEN_PREFIX = "Bearer";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String USER_NAME_PROPERTY = "username";
    private static final String PASSWORD_PROPERTY = "password";

    private static UserService userService;
    private static TokenService tokenService;
    public static Route login = (Request req, Response resp) -> {

        String body = "";
        String username = null;
        String password = null;

        if (req.body() != null) {
            username = JSONUtil.readPropertyFromJson(req.body(), USER_NAME_PROPERTY);
            password = JSONUtil.readPropertyFromJson(req.body(), PASSWORD_PROPERTY);
        }
        if (username != null && password != null) {
            try {

                Optional<UserResource> user = userService.getUserByUserName(username);
                user.orElseThrow(() -> new Exception("User is not registered"));

                BCrypt.Result result = BCrypt.verifyer().verify(password.getBytes(StandardCharsets.UTF_8), user.get().getEncryptedPassword());

                if (result.verified) {
                    String token = tokenService.newToken(user.get());
                    resp.header(AUTHORIZATION_HEADER, TOKEN_PREFIX + " " + token);

                    ObjectMapper mapper = new ObjectMapper();

                    // create a JSON object
                    ObjectNode jsonBody = mapper.createObjectNode();
                    jsonBody.put("id", user.get().getId());
                    jsonBody.put("token", TOKEN_PREFIX + " " + token);

                    // convert `ObjectNode` to pretty-print JSON
                    // without pretty-print, use `user.toString()` method
                    StringWriter sw = new StringWriter();
                    mapper.writeValue(sw, jsonBody);
                    body = sw.toString();

                    resp.status(200);

                } else {
                    resp.body("Incorrect password");
                    resp.status(401);
                }
            } catch (Exception e) {
                resp.status(401);
            }
        }

        return body;
    };

    public static Route logout = (Request req, Response resp) -> {
        String authorizationHeader = req.headers(AUTHORIZATION_HEADER);
        tokenService.revokeToken(authorizationHeader.replace(TOKEN_PREFIX, ""));
        return "";
    };

    @Autowired
    public AuthController(UserService userService, TokenService tokenService) {
        super(tokenService);
        this.userService = userService;
        this.tokenService = tokenService;
    }

}
