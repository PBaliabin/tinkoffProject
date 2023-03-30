package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.inteface.GitHubClient;

@Service
public class GitHubClientService {
    private final GitHubClient webClient;

    public GitHubClientService(@Qualifier("gitHubWebClient") GitHubClient webClient) {
        this.webClient = webClient;
    }

    public GitHubResponse getRepo(String owner, String repo) {
        return webClient.getRepository(owner, repo);
    }
}
