package com.asapp.backend.challenge.controller;


import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.LongPasswordStrategies;
import com.asapp.backend.challenge.resources.UserResource;
import com.asapp.backend.challenge.service.UserService;
import com.asapp.backend.challenge.utils.CreateUserResponseSerializer;
import com.asapp.backend.challenge.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spark.Request;
import spark.Response;
import spark.Route;

import java.nio.charset.StandardCharsets;

@Component
public class UsersController {

    public static UserService userService;
    public static Route createUser = (Request req, Response resp) -> {
        resp.type("application/json");
        UserResource user = (UserResource) JSONUtil.jsonToData(req.body(), UserResource.class);

        return JSONUtil.dataToJsonWithCustomSerializer(userService.save(user), new CreateUserResponseSerializer(), UserResource.class);
    };
    public static Route getUser = (Request req, Response resp) -> {
        resp.type("application/json");
        Long id = Long.valueOf(req.queryParams("id"));
        return userService.getUser(id)
                .orElseThrow(() -> new Exception("User not found - " + id));
    };

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }
}
