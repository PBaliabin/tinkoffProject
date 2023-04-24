package ru.tinkoff.edu.java.scrapper.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.GithubLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.GithubLinkRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.StackoverflowLinkRecord;
import ru.tinkoff.edu.java.scrapper.dto.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.inteface.DateUtil;

import java.sql.Timestamp;

@Service
public class TypeConverter {
    public GithubLinkRecord makeGithubLinkRecord(String link, GitHubResponse gitHubResponse) {
        long newUpdateTime = 0L;
        try {
            newUpdateTime = DateUtil.provideDateFormat().parse(gitHubResponse.getUpdatedAt()).getTime();
        } catch (Exception e) {
            System.out.println("Error while parsing GitHub Timestamp");
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

    public GithubLink makeGithubLink(GithubLinkRecord githubLinkRecord) {
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

    public StackoverflowLinkRecord makeStackoverflowLinkRecord(String link, StackoverflowResponse stackoverflowResponse) {
        return new StackoverflowLinkRecord(
                link,
                stackoverflowResponse.getQuotaMax(),
                stackoverflowResponse.getQuotaRemaining(),
                new Timestamp(Long.parseLong(stackoverflowResponse.getLastActivityDate())).toLocalDateTime(),
                stackoverflowResponse.getIsAnswered(),
                stackoverflowResponse.getAnswerCount(),
                new Timestamp(System.currentTimeMillis()).toLocalDateTime()
        );
    }

    public StackoverflowLink makeStackoverflowLink(StackoverflowLinkRecord stackoverflowLinkRecord) {
        return new StackoverflowLink(
                stackoverflowLinkRecord.getLink(),
                stackoverflowLinkRecord.getQuotaMax(),
                stackoverflowLinkRecord.getQuotaRemaining(),
                stackoverflowLinkRecord.getLastActivityTime(),
                stackoverflowLinkRecord.getIsAnswered(),
                stackoverflowLinkRecord.getAnswerCount(),
                stackoverflowLinkRecord.getLastCheckTime()
        );
    }
}
