package com.asapp.backend.challenge.exceptions;

public class MessageTypeException extends Exception {
    public MessageTypeException() {
        super("Message with type not allowed");
    }
}
