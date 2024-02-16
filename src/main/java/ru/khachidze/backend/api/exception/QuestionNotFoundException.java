package ru.khachidze.backend.api.exception;

public class QuestionNotFoundException extends CustomEntityNotFoundException {
    public QuestionNotFoundException(String message) {
        super(message);
    }
}
