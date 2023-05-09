package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.bot.model.exception.BadRequestException;
import ru.tinkoff.edu.java.bot.model.response.ApiErrorResponse;

import java.util.Arrays;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse badRequestException(BadRequestException badRequestException) {
        return new ApiErrorResponse(
                "This is bad request response description",
                "400",
                "BadRequestException",
                "Что-то пошло не по плану",
                Arrays
                        .stream(badRequestException.getStackTrace())
                        .map(StackTraceElement::toString)
                        .toList());
    }
}
