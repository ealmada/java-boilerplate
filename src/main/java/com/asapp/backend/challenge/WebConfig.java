package com.asapp.backend.challenge;

import com.asapp.backend.challenge.controller.AuthController;
import com.asapp.backend.challenge.controller.HealthController;
import com.asapp.backend.challenge.controller.MessagesController;
import com.asapp.backend.challenge.controller.UsersController;
import com.asapp.backend.challenge.exceptions.IdMissingException;
import com.asapp.backend.challenge.filter.TokenValidatorFilter;
import com.asapp.backend.challenge.security.TokenService;
import com.asapp.backend.challenge.service.MessageService;
import com.asapp.backend.challenge.service.UserService;
import com.asapp.backend.challenge.utils.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spark.Spark;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static spark.Spark.exception;

@Component
public class WebConfig {


    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private final UserService userService;
    private final TokenService tokenService;
    private final MessageService messageService;

    @Autowired
    public WebConfig(UserService userService, TokenService tokenService, MessageService messageService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.messageService = messageService;
        init();
    }

    private void init() {

        // PERIODIC TOKENS CLEAN UP
        EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
            System.out.println("Removing expired tokens");
            tokenService.removeExpired();

            //Simulate update metadata from web with hardcoded, the model lets you use more than 1 metadata
            //Ideally it should be separated in other microservice consuming this data and uploading it
            messageService.updateMetadata();

        }, 60, 60, TimeUnit.SECONDS); // every minute

        exception(Exception.class, (e, request, response) -> {
            System.err.println("Exception while processing request");
            e.printStackTrace();
        });


        // Spark Configuration
        Spark.port(8085);

        // Configure Endpoints
        // Users
        Spark.post(Path.USERS, UsersController.createUser);
        Spark.get(Path.USERS, UsersController.getUser);
        // Auth
        Spark.post(Path.LOGIN, AuthController.login);
        // Messages
        Spark.before(Path.MESSAGES, TokenValidatorFilter.validateUser);
        Spark.post(Path.MESSAGES, MessagesController.sendMessage);
        Spark.get(Path.MESSAGES, MessagesController.getMessages);
        // Health
        Spark.post(Path.HEALTH, HealthController.check);

        exception(IdMissingException.class, (e, request, response) -> {
            response.status(404);
            response.body("Recipient does not exist in db");
        });

    }
}
