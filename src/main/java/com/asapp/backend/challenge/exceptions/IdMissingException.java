package com.asapp.backend.challenge.exceptions;

public class IdMissingException extends Exception {
    public IdMissingException() {
        super("Id is missing or wrong in request parameter");
    }
}
