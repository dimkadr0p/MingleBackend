package ru.khachidze.backend.api.exception;

public class EmailAlreadyExistsException extends EntityAlreadyExistsException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
