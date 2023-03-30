package ru.tinkoff.edu.java.scrapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.client.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.dto.*;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScrapperController {

    private final GitHubClientService gitHubClientService;
    private final StackOverflowClientService stackOverflowClientService;

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
            throw new BadRequestException(new ApiErrorResponse("tg-chat with id=" + id + " already exists",
                    "400",
                    "AlreadyExistsException",
                    "Некорректные параметры запроса",
                    new ArrayList<>()));
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
            throw new BadRequestException(new ApiErrorResponse("wrong input data",
                    "400",
                    "BadRequestException",
                    "Некорректные параметры запроса",
                    new ArrayList<>()));
        } else {
            throw new NotFoundException(new ApiErrorResponse("tg-chat with id=" + id + " doesn't exists",
                    "404",
                    "NoSuchElementException",
                    "Чат не существует",
                    new ArrayList<>()));
        }
    }

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
    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getAllLinks(@RequestHeader(value = "Tg-Chat-Id") long tgChatId) {
        int random = (int) (Math.random() * 10);
        random = random % 2;

        if (random == 1) {
            return ResponseEntity.ok(new ListLinksResponse(
                    (ArrayList<LinkResponse>) List.of(
                            new LinkResponse(1, URI.create("url1")),
                            new LinkResponse(2, URI.create("url2"))),
                    2));
        } else {
            throw new BadRequestException(new ApiErrorResponse("tg-chat with id=" + tgChatId + " doesn't exists",
                    "400",
                    "NoSuchElementException",
                    "Чат не существует",
                    new ArrayList<>()));
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
    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLink(@RequestBody AddLinkRequest link, @RequestHeader(value = "Tg-Chat-Id") long tgChatId) {
        int random = (int) (Math.random() * 10);
        random = random % 2;

        if (random == 1) {
            return ResponseEntity.ok(new LinkResponse(1, link.getLink()));
        } else if (link.getLink() == null) {
            throw new BadRequestException(new ApiErrorResponse("EmptyRequestBody",
                    "400",
                    "EmptyRequestBodyException",
                    "Некорректные параметры запроса",
                    new ArrayList<>()));
        } else {
            throw new NotFoundException(new ApiErrorResponse("tg-chat with id=" + tgChatId + " already exists",
                    "400",
                    "AlreadyExistsException",
                    "Некорректные параметры запроса",
                    new ArrayList<>()));
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
    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> removeLink(@RequestBody RemoveLinkRequest link, @RequestHeader(value = "Tg-Chat-Id") long tgChatId) {
        int random = (int) (Math.random() * 10);
        random = random % 2;

        if (random == 1) {
            return ResponseEntity.ok(new LinkResponse(1, link.getLink()));
        } else if (link.getLink() == null) {
            throw new BadRequestException(new ApiErrorResponse("EmptyRequestBody",
                    "400",
                    "EmptyRequestBodyException",
                    "Некорректные параметры запроса",
                    new ArrayList<>()));
        } else {
            throw new NotFoundException(new ApiErrorResponse("tg-chat with id=" + tgChatId + " doesn't exists",
                    "404",
                    "NoSuchElementException",
                    "Чат не существует",
                    new ArrayList<>()));
        }
    }

    @GetMapping("/repos/{owner}/{repo}")
    public GitHubResponse getRepository(@PathVariable String owner, @PathVariable String repo) {
        return gitHubClientService.getRepo(owner, repo);
    }

    @GetMapping("/questions/{questionNumber}")
    public StackoverflowResponse getQuestion(@PathVariable String questionNumber, @RequestParam("site") String site) {
        return stackOverflowClientService.getQuestion(questionNumber, site);
    }
}
