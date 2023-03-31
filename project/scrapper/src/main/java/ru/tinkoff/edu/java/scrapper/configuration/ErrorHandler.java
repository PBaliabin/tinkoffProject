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

@Hidden
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse badRequestException(BadRequestException badRequestException) {
        return new ApiErrorResponse("This is bad request response description",
                "400",
                "BadRequestException",
                "Что-то пошло не по плану",
                Arrays
                        .stream(badRequestException.getStackTrace())
                        .map(StackTraceElement::toString)
                        .toList());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse notFoundException(NotFoundException notFoundException) {
        return new ApiErrorResponse("This is not found response description",
                "404",
                "NotFoundException",
                "Запрашиваемый элемент не был найден",
                Arrays
                        .stream(notFoundException.getStackTrace())
                        .map(StackTraceElement::toString)
                        .toList());
    }
}
