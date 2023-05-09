package ru.tinkoff.edu.java.bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.model.exception.BadRequestException;
import ru.tinkoff.edu.java.bot.model.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.model.response.ApiErrorResponse;

@Slf4j
@RestController
public class BotController {


    @Operation(summary = "Отправить обновление")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Обновление обработано"),
            @ApiResponse(responseCode = "400",
                         description = "Некорректные параметры запроса",
                         content = {
                                 @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                          schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @PostMapping(value = "/updates")
    public void updateData(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        if (linkUpdateRequest.getId() % 2 > 0) {
            log.info("Обновление обработано");
        } else {
            throw new BadRequestException();
        }
    }
}
