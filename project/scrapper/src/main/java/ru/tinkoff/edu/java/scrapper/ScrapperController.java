package ru.tinkoff.edu.java.scrapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.client.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClientService;
import ru.tinkoff.edu.java.scrapper.dto.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.StackoverflowResponse;

@RestController
@RequiredArgsConstructor
public class ScrapperController {

    private final GitHubClientService gitHubClientService;
    private final StackOverflowClientService stackOverflowClientService;

    @GetMapping("/repos/{owner}/{repo}")
    public GitHubResponse getRepository(@PathVariable String owner, @PathVariable String repo) {
        return gitHubClientService.getRepo(owner, repo);
    }

    @GetMapping("/questions/{questionNumber}")
    public StackoverflowResponse getQuestion(@PathVariable String questionNumber,
                                             @RequestParam(value = "site", required = false, defaultValue = "stackoverflow") String site) {
        return stackOverflowClientService.getQuestion(questionNumber, site);
    }
}
