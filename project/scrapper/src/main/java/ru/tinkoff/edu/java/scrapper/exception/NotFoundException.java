package ru.tinkoff.edu.java.scrapper.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotFoundException extends RuntimeException {
    public ApiErrorResponse apiErrorResponse;
}
