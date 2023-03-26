package ru.tinkoff.edu.java.bot;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.exception.BadRequestException;

import java.util.ArrayList;

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
    public ResponseEntity<Void> updateData(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        int random = (int) (Math.random() * 10);
        random = random % 2;

        if (random == 1) {
            System.out.println("all good");
            return ResponseEntity.ok().build();
        } else {
            throw new BadRequestException(new ApiErrorResponse(linkUpdateRequest.getDescription(),
                    "400",
                    "NotFoundException",
                    "Некорректные параметры запроса",
                    new ArrayList<>()));
        }
    }
}
