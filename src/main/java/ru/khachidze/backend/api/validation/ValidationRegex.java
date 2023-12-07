package ru.khachidze.backend.api.validation;


public class ValidationRegex {
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,100}$";
    public static final String LOGIN_REGEX = "^[a-zA-Z_][a-zA-Z0-9_]{4,20}$";
}
