package ru.tinkoff.edu.java.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.inteface.GitHubClient;

@Service
@RequiredArgsConstructor
public class GitHubClientService {
    private final GitHubClient webClient;

    public GitHubResponse getRepo(String owner, String repo) {
        return webClient.getRepository(owner, repo);
    }
}
