package ru.tinkoff.edu.java.scrapper.jpa.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.inteface.service.GithubLinkService;
import ru.tinkoff.edu.java.scrapper.jpa.entity.GithubLink;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaGithubLinkRepository;
import ru.tinkoff.edu.java.scrapper.jpa.util.JpaTypeConverter;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JpaGithubLinkService implements GithubLinkService<GithubLink> {
    private final JpaGithubLinkRepository jpaGithubLinkRepository;
    private final GitHubClientService gitHubClientService;
    private final JpaTypeConverter jpaTypeConverter;

    @Override
    public int add(URI url) {
        Map<String, String> parsedLink = Parser.parseLink(url.toString());
        GitHubResponse gitHubResponse = gitHubClientService.getRepo(
                parsedLink.get(GithubLinkService.OWNER),
                parsedLink.get(GithubLinkService.REPOSITORY));
        GithubLink githubLink = jpaTypeConverter.makeGithubLink(url.toString(), gitHubResponse);
        return List.of(jpaGithubLinkRepository.save(githubLink)).size();
    }

    @Override
    public int remove(URI url) {
        jpaGithubLinkRepository.deleteById(url.toString());
        return 1;
    }

    @Override
    public int update(GithubLink githubLink) {
        return List.of(jpaGithubLinkRepository.save(githubLink)).size();
    }

    @Override
    public Collection<GithubLink> getLinksByLastCheckTime(LocalDateTime threshold) {
        return jpaGithubLinkRepository.getGithubLinksByLastCheckTimeBefore(threshold);
    }
}
