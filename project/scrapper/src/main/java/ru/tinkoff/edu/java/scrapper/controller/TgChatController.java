package ru.tinkoff.edu.java.scrapper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.dto.Chat;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.JdbcChatRepository;

@RequestMapping("/tg-chat")
@RestController
@RequiredArgsConstructor
public class TgChatController {

    private final JdbcChatRepository jdbcChatRepository;

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
    @PostMapping("/{id}")
    public void signInChat(@PathVariable long id) {
        try {
            jdbcChatRepository.addChat(new Chat(String.valueOf(id)));
            System.out.println("Register chat with id = " + id);
        } catch (Exception e) {
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
    @DeleteMapping("/{id}")
    public void deleteChat(@PathVariable long id) {
        try {
            jdbcChatRepository.deleteChat(new Chat(String.valueOf(id)));
            System.out.println("Unregister chat with id = " + id);
        } catch (BadRequestException e) {
            throw new BadRequestException();
        } catch (NotFoundException e) {
            throw new NotFoundException();
        }
    }
}
