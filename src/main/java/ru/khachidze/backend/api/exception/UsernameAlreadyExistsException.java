package ru.khachidze.backend.api.exception;

public class UsernameAlreadyExistsException extends EntityAlreadyExistsException {
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
