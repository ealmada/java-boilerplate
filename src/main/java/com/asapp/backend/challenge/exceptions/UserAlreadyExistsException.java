package com.asapp.backend.challenge.exceptions;

import com.asapp.backend.challenge.resources.UserResource;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(UserResource user) {
        super("User "+user.getUsername()+" already exists in db");
    }
}
