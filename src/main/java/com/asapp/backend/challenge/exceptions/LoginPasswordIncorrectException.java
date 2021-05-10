package com.asapp.backend.challenge.exceptions;

import com.asapp.backend.challenge.resources.UserResource;

public class LoginPasswordIncorrectException extends Exception {
    public LoginPasswordIncorrectException(UserResource user) {
        super("User "+user.getUsername()+" has requested access with incorrect password");
    }
}
