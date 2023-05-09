package ru.tinkoff.edu.java.scrapper.dto.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.dto.response.ApiErrorResponse;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class NotFoundException extends RuntimeException {
    public ApiErrorResponse apiErrorResponse;
}
