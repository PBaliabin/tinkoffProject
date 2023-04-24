package ru.tinkoff.edu.java.scrapper.jdbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.dto.entity.GithubLink;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.inteface.service.GithubLinkService;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcGithubLinkRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.util.JdbcTypeConverter;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JdbcGithubLinkService implements GithubLinkService<GithubLink> {
    private final JdbcGithubLinkRepository jdbcGithubLinkRepository;
    private final GitHubClientService gitHubClientService;
    private final JdbcTypeConverter jdbcTypeConverter;

    @Override
    public int add(URI url) {
        Map<String, String> parsedLink = Parser.parseLink(url.toString());
        GitHubResponse gitHubResponse = gitHubClientService.getRepo(
                parsedLink.get(GithubLinkService.OWNER),
                parsedLink.get(GithubLinkService.REPOSITORY));
        GithubLink githubLink = jdbcTypeConverter.makeGithubLink(url.toString(), gitHubResponse);
        return jdbcGithubLinkRepository.add(githubLink);
    }

    @Override
    public int remove(URI url) {
        return jdbcGithubLinkRepository.remove(url);
    }

    @Override
    public int update(GithubLink githubLink) {
        return jdbcGithubLinkRepository.update(githubLink);
    }

    @Override
    public Collection<GithubLink> getLinksByLastCheckTime(LocalDateTime threshold) {
        return jdbcGithubLinkRepository.getLinksBeforeLastCheckTime(threshold);
    }
}
