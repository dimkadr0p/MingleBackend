package ru.khachidze.backend.api.advice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.khachidze.backend.api.exception.AppError;
import ru.khachidze.backend.api.exception.CustomEntityNotFoundException;
import ru.khachidze.backend.api.exception.UserNotFoundException;

@ControllerAdvice
@Slf4j
public class NotFoundExceptionHandler {
    @ResponseBody
    @ExceptionHandler(CustomEntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public AppError handleNotFoundException(UserNotFoundException e) {
        return new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

}
