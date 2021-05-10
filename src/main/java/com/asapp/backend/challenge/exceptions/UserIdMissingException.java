package com.asapp.backend.challenge.exceptions;

public class UserIdMissingException extends Exception {
    public UserIdMissingException() {
        super("User Id is missing or wrong in request parameter");
    }
}
