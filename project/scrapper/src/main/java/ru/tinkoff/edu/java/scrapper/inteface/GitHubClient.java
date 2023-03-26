package ru.tinkoff.edu.java.scrapper.inteface;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import ru.tinkoff.edu.java.scrapper.dto.GitHubResponse;

public interface GitHubClient {
    //@GetExchange("/repos/PBaliabin/tinkoffProject")
    @GetExchange("/repos/{owner}/{repo}")
    GitHubResponse getRepository(@PathVariable String owner, @PathVariable String repo);
}
