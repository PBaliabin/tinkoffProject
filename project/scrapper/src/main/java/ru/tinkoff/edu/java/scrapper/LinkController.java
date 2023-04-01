package ru.tinkoff.edu.java.scrapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.*;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;

import java.net.URI;
import java.util.List;

@RequestMapping("/links")
@RestController
public class LinkController {

    @Operation(summary = "Получить все отслеживаемые ссылки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ссылки успешно получены",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ListLinksResponse.class))}),

            @ApiResponse(responseCode = "400",
                    description = "Некорректные параметры запроса",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping()
    public ResponseEntity<ListLinksResponse> getAllLinks(@RequestHeader(value = "Tg-Chat-Id") long tgChatId) {
        int random = (int) (Math.random() * 10);
        random = random % 2;

        if (random == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(new ListLinksResponse(
                    List.of(
                            new LinkResponse(1, URI.create("url1")),
                            new LinkResponse(2, URI.create("url2"))),
                    2));
        } else {
            throw new BadRequestException();
        }
    }

    @Operation(summary = "Добавить отслеживание ссылки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ссылка успешно добавлена",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LinkResponse.class))}),

            @ApiResponse(responseCode = "400",
                    description = "Некорректные параметры запроса",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @PostMapping()
    public ResponseEntity<LinkResponse> addLink(@RequestBody AddLinkRequest link, @RequestHeader(value = "Tg-Chat-Id") long tgChatId) {
        int random = (int) (Math.random() * 10);
        random = random % 2;

        if (random == 1) {
            return ResponseEntity.ok(new LinkResponse(1, link.getLink()));
        } else {
            throw new BadRequestException();
        }
    }


    @Operation(summary = "Убрать отслеживание ссылки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ссылка успешно убрана",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LinkResponse.class))}),

            @ApiResponse(responseCode = "400",
                    description = "Некорректные параметры запроса",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Ссылка не найдена",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @DeleteMapping()
    public ResponseEntity<LinkResponse> removeLink(@RequestBody RemoveLinkRequest link, @RequestHeader(value = "Tg-Chat-Id") long tgChatId) {
        int random = (int) (Math.random() * 10);
        random = random % 3;

        if (random == 1) {
            return ResponseEntity.ok(new LinkResponse(1, link.getLink()));
        } else if (random == 2) {
            throw new BadRequestException();
        } else {
            throw new NotFoundException();
        }
    }
}
