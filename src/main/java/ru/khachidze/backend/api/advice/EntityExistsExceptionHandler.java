package ru.khachidze.backend.api.advice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.khachidze.backend.api.exception.AppError;
import ru.khachidze.backend.api.exception.EntityAlreadyExistsException;

@ControllerAdvice
@Slf4j
public class EntityExistsExceptionHandler {

    @ResponseBody
    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public AppError handleUsernameAlreadyExistsException(EntityAlreadyExistsException e) {
        log.info("In EntityExistsExceptionHandler -> handleUsernameAlreadyExistsException(): {}", e.getMessage());
        return new AppError(HttpStatus.CONFLICT.value(), e.getMessage());
    }
}