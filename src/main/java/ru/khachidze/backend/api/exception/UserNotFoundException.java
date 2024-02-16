package ru.khachidze.backend.api.exception;

public class UserNotFoundException extends CustomEntityNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
