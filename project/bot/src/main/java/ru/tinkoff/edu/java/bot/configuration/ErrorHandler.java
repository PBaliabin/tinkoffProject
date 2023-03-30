package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.bot.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.exception.BadRequestException;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse badRequestException(BadRequestException badRequestException) {
        ApiErrorResponse apiErrorResponse = badRequestException.getApiErrorResponse();
        List<String> stackTrace = Arrays
                .stream(badRequestException.getStackTrace())
                .map(StackTraceElement::toString)
                .toList();
        apiErrorResponse.setStacktrace(stackTrace);
        return apiErrorResponse;
    }
}
