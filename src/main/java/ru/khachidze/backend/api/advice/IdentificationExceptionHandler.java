package ru.khachidze.backend.api.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.khachidze.backend.api.exception.AppError;
import ru.khachidze.backend.api.exception.CustomAuthenticationException;
import ru.khachidze.backend.api.exception.EntityAlreadyExistsException;

@ControllerAdvice
@Slf4j
public class IdentificationExceptionHandler {
    @ExceptionHandler(CustomAuthenticationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AppError handleBadCredentialsException(CustomAuthenticationException e) {
        String errorMessage = "Неверные учетные данные";
        log.info("In EntityExistsExceptionHandler -> handleUsernameAlreadyExistsException(): {}", e.getMessage());
        return new AppError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }
}
