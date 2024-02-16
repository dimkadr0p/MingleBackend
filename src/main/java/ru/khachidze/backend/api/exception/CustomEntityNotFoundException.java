package ru.khachidze.backend.api.exception;

public class CustomEntityNotFoundException extends RuntimeException {
    public CustomEntityNotFoundException(String name) {
        super(name);
    }
}
