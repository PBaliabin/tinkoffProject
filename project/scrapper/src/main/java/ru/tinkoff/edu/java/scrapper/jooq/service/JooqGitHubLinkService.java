package ru.tinkoff.edu.java.scrapper.jooq.service;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import ru.tinkoff.edu.java.linkParser.Parser;
import ru.tinkoff.edu.java.scrapper.client.GitHubClientService;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.GithubLinkRecord;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.inteface.service.GithubLinkService;
import ru.tinkoff.edu.java.scrapper.jooq.util.converter.JooqGithubLinkConverter;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class JooqGitHubLinkService implements GithubLinkService<GithubLinkRecord> {

    private final DSLContext dslContext;
    private final GitHubClientService gitHubClientService;
    private final JooqGithubLinkConverter jooqGithubLinkConverter;
    private final ru.tinkoff.edu.java.scrapper.domain.jooq.tables.GithubLink githubLinkTable;

    @Override
    public void add(URI url) {
        Map<String, String> parsedLink = Parser.parseLink(url.toString());
        GitHubResponse gitHubResponse = gitHubClientService.getRepo(
                parsedLink.get(GithubLinkService.OWNER),
                parsedLink.get(GithubLinkService.REPOSITORY));
        GithubLinkRecord githubLinkRecord = jooqGithubLinkConverter.makeGithubLinkRecord(url.toString(), gitHubResponse);
        dslContext.insertInto(githubLinkTable).values(githubLinkRecord.fields()).execute();
    }

    @Override
    public void remove(URI uri) {
        dslContext
                .deleteFrom(githubLinkTable)
                .where(githubLinkTable.LINK.eq(uri.toString()))
                .execute();
    }

    @Override
    public void update(GithubLinkRecord githubLinkRecord) {
        dslContext.update(githubLinkTable).set(githubLinkRecord).execute();
    }

    @Override
    public Collection<GithubLinkRecord> getLinksByLastCheckTime(LocalDateTime threshold) {
        return dslContext
                .selectFrom(githubLinkTable)
                .where(githubLinkTable.LAST_CHECK_TIME.lessOrEqual(threshold))
                .fetch();
    }
}
