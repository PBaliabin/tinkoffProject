package ru.tinkoff.edu.java.scrapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.client.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.client.ScrapperClientService;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.dto.*;

@RestController
@RequiredArgsConstructor
public class ScrapperController {

    private final GitHubClientService gitHubClientService;
    private final StackOverflowClientService stackOverflowClientService;
    private final ScrapperClientService scrapperClientService;

    @GetMapping("/repos/{owner}/{repo}")
    public GitHubResponse getRepository(@PathVariable String owner, @PathVariable String repo) {
        return gitHubClientService.getRepo(owner, repo);
    }

    @GetMapping("/questions/{questionNumber}")
    public StackoverflowResponse getQuestion(@PathVariable String questionNumber,
                                             @RequestParam(value = "site", required = false, defaultValue = "stackoverflow") String site) {
        return stackOverflowClientService.getQuestion(questionNumber, site);
    }

    @GetMapping("/links")
    public ListLinksResponse getAllLinks(@RequestHeader(value = "Tg-Chat-Id") long tgChatId) {
        return scrapperClientService.getAllLinks(tgChatId);
    }

    @PostMapping("/links")
    public LinkResponse addLink(@RequestBody AddLinkRequest link, @RequestHeader(value = "Tg-Chat-Id") long tgChatId) {
        return scrapperClientService.addLink(link, tgChatId);
    }

    @DeleteMapping("/links")
    public LinkResponse removeLink(@RequestBody RemoveLinkRequest link, @RequestHeader(value = "Tg-Chat-Id") long tgChatId) {
        return scrapperClientService.removeLink(link, tgChatId);
    }

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<Void> signInChat(@PathVariable long id) {
        return scrapperClientService.signInChat(id);
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable long id) {
        return scrapperClientService.deleteChat(id);
    }
}
