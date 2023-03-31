package ru.tinkoff.edu.java.scrapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;

@RestController
public class TgChatController {

    @Operation(summary = "Зарегистрировать чат")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Чат зарегистрирован"),
            @ApiResponse(responseCode = "400",
                    description = "Некорректные параметры запроса",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @PostMapping("/tg-chat/{id}")
    public void signInChat(@PathVariable long id) {
        int random = (int) (Math.random() * 10);
        random = random % 2;

        if (random == 1) {
            System.out.println("Чат зарегистрирован");
        } else {
            throw new BadRequestException();
        }
    }

    @Operation(summary = "Удалить чат")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Чат успешно удалён"),
            @ApiResponse(responseCode = "400",
                    description = "Некорректные параметры запроса",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Чат не существует",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @DeleteMapping("/tg-chat/{id}")
    public void deleteChat(@PathVariable long id) {
        int random = (int) (Math.random() * 10);
        random = random % 3;

        if (random == 1) {
            System.out.println("Чат удален");
        } else if (random == 2) {
            throw new BadRequestException();
        } else {
            throw new NotFoundException();
        }
    }
}
