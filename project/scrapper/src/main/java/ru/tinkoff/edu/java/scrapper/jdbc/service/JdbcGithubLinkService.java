package ru.tinkoff.edu.java.scrapper.jdbc.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.dto.entity.GithubLink;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.inteface.service.GithubLinkService;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcGithubLinkRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.util.converter.JdbcGithubLinkConverter;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class JdbcGithubLinkService implements GithubLinkService<GithubLink> {
    private final JdbcGithubLinkRepository jdbcGithubLinkRepository;
    private final GitHubClientService gitHubClientService;
    private final JdbcGithubLinkConverter jdbcGithubLinkConverter;

    @Override
    public void add(URI url) {
        Map<String, String> parsedLink = Parser.parseLink(url.toString());
        GitHubResponse gitHubResponse = gitHubClientService.getRepo(
                parsedLink.get(GithubLinkService.OWNER),
                parsedLink.get(GithubLinkService.REPOSITORY));
        GithubLink githubLink = jdbcGithubLinkConverter.makeGithubLink(url.toString(), gitHubResponse);
        jdbcGithubLinkRepository.add(githubLink);
    }

    @Override
    public void remove(URI url) {
        jdbcGithubLinkRepository.remove(url);
    }

    @Override
    public void update(GithubLink githubLink) {
        jdbcGithubLinkRepository.update(githubLink);
    }

    @Override
    public Collection<GithubLink> getLinksByLastCheckTime(LocalDateTime threshold) {
        return jdbcGithubLinkRepository.getLinksBeforeLastCheckTime(threshold);
    }
}
