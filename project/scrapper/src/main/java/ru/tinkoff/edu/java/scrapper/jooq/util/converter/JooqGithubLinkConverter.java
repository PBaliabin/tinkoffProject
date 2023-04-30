package ru.tinkoff.edu.java.scrapper.jooq.util.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.GithubLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.GithubLinkRecord;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.inteface.DateUtil;

import java.sql.Timestamp;

@Slf4j
@Component
public class JooqGithubLinkConverter {
    public GithubLinkRecord makeGithubLinkRecord(String link, GitHubResponse gitHubResponse) {
        long newUpdateTime = 0L;
        try {
            newUpdateTime = DateUtil.provideDateFormat().parse(gitHubResponse.getUpdatedAt()).getTime();
        } catch (Exception e) {
            log.error("Error while parsing GitHub Timestamp");
        }
        return new GithubLinkRecord(
                link,
                gitHubResponse.getId(),
                gitHubResponse.getName(),
                gitHubResponse.getFullName(),
                new Timestamp(newUpdateTime).toLocalDateTime(),
                gitHubResponse.getForksCount(),
                gitHubResponse.getOpenIssuesCount(),
                new Timestamp(System.currentTimeMillis()).toLocalDateTime()
        );
    }

    public ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.GithubLink makeGithubLink(GithubLinkRecord githubLinkRecord) {
        return new GithubLink(
                githubLinkRecord.getLink(),
                githubLinkRecord.getRepositoryId(),
                githubLinkRecord.getName(),
                githubLinkRecord.getFullName(),
                githubLinkRecord.getLastActivityTime(),
                githubLinkRecord.getForksCount(),
                githubLinkRecord.getOpenIssuesCount(),
                githubLinkRecord.getLastCheckTime()
        );
    }

    public ru.tinkoff.edu.java.scrapper.dto.entity.GithubLink convertJooqGithubLinkToCustom(GithubLink githubLink) {
        return new ru.tinkoff.edu.java.scrapper.dto.entity.GithubLink(
                githubLink.getLink(),
                githubLink.getRepositoryId(),
                githubLink.getName(),
                githubLink.getFullName(),
                githubLink.getLastActivityTime(),
                githubLink.getForksCount(),
                githubLink.getOpenIssuesCount(),
                githubLink.getLastCheckTime()
        );
    }
}
