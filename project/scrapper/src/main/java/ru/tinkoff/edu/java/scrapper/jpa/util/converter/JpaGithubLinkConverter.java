package ru.tinkoff.edu.java.scrapper.jpa.util.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.inteface.DateUtil;
import ru.tinkoff.edu.java.scrapper.jpa.entity.GithubLink;

import java.sql.Timestamp;

@Slf4j
@Component
public class JpaGithubLinkConverter {
    public ru.tinkoff.edu.java.scrapper.jpa.entity.GithubLink makeGithubLink(String link, GitHubResponse gitHubResponse) {
        long newUpdateTime = 0L;
        try {
            newUpdateTime = DateUtil.provideDateFormat().parse(gitHubResponse.getUpdatedAt()).getTime();
        } catch (Exception e) {
            log.error("Error while parsing GitHub Timestamp");
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
}
