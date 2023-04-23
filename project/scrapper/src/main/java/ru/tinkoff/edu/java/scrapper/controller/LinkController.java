package ru.tinkoff.edu.java.scrapper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatToLinkRecord;
import ru.tinkoff.edu.java.scrapper.dto.*;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;
import ru.tinkoff.edu.java.scrapper.inteface.GitHubClient;
import ru.tinkoff.edu.java.scrapper.inteface.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.jooq.JooqChatToLinkService;
import ru.tinkoff.edu.java.scrapper.jooq.JooqGitHubLinkService;
import ru.tinkoff.edu.java.scrapper.jooq.JooqStackoverflowLinkService;
import ru.tinkoff.edu.java.scrapper.service.TypeConverter;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/links")
@RestController
@RequiredArgsConstructor
public class LinkController {

    private final JooqChatToLinkService jooqChatToLinkService;
    private final JooqGitHubLinkService jooqGitHubLinkService;
    private final JooqStackoverflowLinkService jooqStackoverflowLinkService;
    private final GitHubClientService gitHubClientService;
    private final StackOverflowClientService stackOverflowClientService;
    private final TypeConverter typeConverter;


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
        try {
            List<LinkResponse> responseList = new ArrayList<>();
            List<ChatToLinkRecord> links = (List<ChatToLinkRecord>) jooqChatToLinkService.getLinksById(tgChatId);
            for (int i = 0; i < links.size(); i++) {
                responseList.add(i, new LinkResponse(i, new URI(links.get(i).getLink())));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ListLinksResponse(responseList, responseList.size()));
        } catch (Exception e) {
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
        try {
            jooqChatToLinkService.add(tgChatId, link.getLink());
            Map<String, String> parsedLink = Parser.parseLink(link.getLink().toString());
            switch (parsedLink.get("domain")){
                case "github.com" -> {
                    GitHubResponse gitHubResponse = gitHubClientService.getRepo(parsedLink.get("owner"), parsedLink.get("repository"));
                    jooqGitHubLinkService.add(
                            typeConverter.makeGithubLink(
                                    typeConverter.makeGithubLinkRecord(
                                            link.getLink().toString(),
                                            gitHubResponse)));
                }
                case "stackoverflow.com" -> {
                    StackoverflowResponse stackoverflowResponse = stackOverflowClientService.getQuestion(parsedLink.get("questionId"), "stackoverflow");
                    jooqStackoverflowLinkService.add(
                            typeConverter.makeStackoverflowLink(
                                    typeConverter.makeStackoverflowLinkRecord(
                                            link.getLink().toString(),
                                            stackoverflowResponse)));
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(new LinkResponse(tgChatId, link.getLink()));
        } catch (BadRequestException e) {
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
        try {
            LinkResponse linkToRemove = new LinkResponse(tgChatId, link.getLink());
            jooqChatToLinkService.remove(tgChatId, linkToRemove.getUrl());
            return ResponseEntity.status(HttpStatus.OK).body(linkToRemove);
        } catch (BadRequestException e) {
            throw new BadRequestException();
        } catch (NotFoundException e) {
            throw new NotFoundException();
        }
    }
}
