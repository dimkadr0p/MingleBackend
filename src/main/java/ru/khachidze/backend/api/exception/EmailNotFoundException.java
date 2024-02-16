package ru.khachidze.backend.api.exception;


public class EmailNotFoundException extends CustomEntityNotFoundException {
    public EmailNotFoundException(String name) {
        super(name);
    }
}
