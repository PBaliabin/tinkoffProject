package ru.tinkoff.edu.java.scrapper.jooq.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.service.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.GithubLinkRecord;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.inteface.service.GithubLinkService;
import ru.tinkoff.edu.java.scrapper.jooq.repository.JooqGithubLinkRepository;
import ru.tinkoff.edu.java.scrapper.jooq.util.converter.JooqGithubLinkConverter;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class JooqGitHubLinkService implements GithubLinkService<GithubLinkRecord> {

    private final JooqGithubLinkRepository jooqGithubLinkRepository;
    private final GitHubClientService gitHubClientService;
    private final JooqGithubLinkConverter jooqGithubLinkConverter;

    @Override
    public void add(URI url) {
        Map<String, String> parsedLink = Parser.parseLink(url.toString());
        GitHubResponse gitHubResponse = gitHubClientService.getRepo(
                parsedLink.get(GithubLinkService.OWNER),
                parsedLink.get(GithubLinkService.REPOSITORY));
        jooqGithubLinkRepository.add(jooqGithubLinkConverter.makeGithubLinkRecord(url.toString(), gitHubResponse));
    }

    @Override
    public void remove(URI uri) {
        jooqGithubLinkRepository.remove(uri);
    }

    @Override
    public void update(GithubLinkRecord githubLinkRecord) {
        jooqGithubLinkRepository.update(githubLinkRecord);
    }

    @Override
    public Collection<GithubLinkRecord> getLinksByLastCheckTime(LocalDateTime threshold) {
        return jooqGithubLinkRepository.getLinksByLastCheckTime(threshold);
    }
}
