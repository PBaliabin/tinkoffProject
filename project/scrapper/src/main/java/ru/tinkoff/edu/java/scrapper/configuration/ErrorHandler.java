package ru.tinkoff.edu.java.scrapper.configuration;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

@Hidden
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

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse notFoundException(NotFoundException notFoundException) {
        ApiErrorResponse apiErrorResponse = notFoundException.getApiErrorResponse();
        List<String> stackTrace = Arrays
                .stream(notFoundException.getStackTrace())
                .map(StackTraceElement::toString)
                .toList();
        apiErrorResponse.setStacktrace(stackTrace);
        return apiErrorResponse;
    }
}
