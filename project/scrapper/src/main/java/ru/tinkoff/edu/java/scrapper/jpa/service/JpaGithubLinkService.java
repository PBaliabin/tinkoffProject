package ru.tinkoff.edu.java.scrapper.jpa.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.inteface.service.GithubLinkService;
import ru.tinkoff.edu.java.scrapper.jpa.entity.GithubLink;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaGithubLinkRepository;
import ru.tinkoff.edu.java.scrapper.jpa.util.converter.JpaGithubLinkConverter;
import ru.tinkoff.edu.java.scrapper.service.GitHubClientService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class JpaGithubLinkService implements GithubLinkService<GithubLink> {
    private final JpaGithubLinkRepository jpaGithubLinkRepository;
    private final GitHubClientService gitHubClientService;
    private final JpaGithubLinkConverter jpaGithubLinkConverter;

    @Override
    public void add(URI url) {
        Map<String, String> parsedLink = Parser.parseLink(url.toString());
        GitHubResponse gitHubResponse = gitHubClientService.getRepo(
                parsedLink.get(GithubLinkService.OWNER),
                parsedLink.get(GithubLinkService.REPOSITORY));
        GithubLink githubLink = jpaGithubLinkConverter.makeGithubLink(url.toString(), gitHubResponse);
        jpaGithubLinkRepository.save(githubLink);
    }

    @Override
    public void remove(URI url) {
        jpaGithubLinkRepository.deleteById(url.toString());
    }

    @Override
    public void update(GithubLink githubLink) {
        jpaGithubLinkRepository.save(githubLink);
    }

    @Override
    public Collection<GithubLink> getLinksByLastCheckTime(LocalDateTime threshold) {
        return jpaGithubLinkRepository.getGithubLinksByLastCheckTimeBefore(threshold);
    }
}
