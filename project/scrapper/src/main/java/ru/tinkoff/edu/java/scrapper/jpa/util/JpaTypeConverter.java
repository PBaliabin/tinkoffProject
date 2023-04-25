package ru.tinkoff.edu.java.scrapper.jpa.util;

import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.inteface.DateUtil;
import ru.tinkoff.edu.java.scrapper.jpa.entity.GithubLink;
import ru.tinkoff.edu.java.scrapper.jpa.entity.StackoverflowLink;

import java.sql.Timestamp;

public class JpaTypeConverter {
    public GithubLink makeGithubLink(String link, GitHubResponse gitHubResponse) {
        long newUpdateTime = 0L;
        try {
            newUpdateTime = DateUtil.provideDateFormat().parse(gitHubResponse.getUpdatedAt()).getTime();
        } catch (Exception e) {
            System.out.println("Error while parsing GitHub Timestamp");
        }
        return new GithubLink(
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

    public StackoverflowLink makeStackoverflowLink(String link, StackoverflowResponse stackoverflowResponse) {
        return new StackoverflowLink(
                link,
                stackoverflowResponse.getQuotaMax(),
                stackoverflowResponse.getQuotaRemaining(),
                new Timestamp(Long.parseLong(stackoverflowResponse.getLastActivityDate())).toLocalDateTime(),
                stackoverflowResponse.getIsAnswered(),
                stackoverflowResponse.getAnswerCount(),
                new Timestamp(System.currentTimeMillis()).toLocalDateTime()
        );
    }

    public ru.tinkoff.edu.java.scrapper.dto.entity.GithubLink convertJpaGithubLinkToCustom(GithubLink githubLink) {
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

    public ru.tinkoff.edu.java.scrapper.dto.entity.StackoverflowLink convertJpaStackoverflowLinkToCustom(StackoverflowLink stackoverflowLink) {
        return new ru.tinkoff.edu.java.scrapper.dto.entity.StackoverflowLink(
                stackoverflowLink.getLink(),
                stackoverflowLink.getQuotaMax(),
                stackoverflowLink.getQuotaRemaining(),
                stackoverflowLink.getLastActivityTime(),
                stackoverflowLink.getIsAnswered(),
                stackoverflowLink.getAnswerCount(),
                stackoverflowLink.getLastCheckTime()
        );
    }
}
