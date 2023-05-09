package ru.tinkoff.edu.java.scrapper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.dto.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.dto.exception.NotFoundException;
import ru.tinkoff.edu.java.scrapper.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.inteface.service.ChatService;

@RequestMapping("/tg-chat")
@RestController
@Slf4j
@RequiredArgsConstructor
public class TgChatController {

    private final ChatService<?> chatService;

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
            chatService.register(id);
            log.info("Register chat with id = "
                             + id);
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
            chatService.unregister(id);
            log.info("Unregister chat with id = "
                             + id);
        } catch (BadRequestException e) {
            throw new BadRequestException();
        } catch (NotFoundException e) {
            throw new NotFoundException();
        }
    }
}
