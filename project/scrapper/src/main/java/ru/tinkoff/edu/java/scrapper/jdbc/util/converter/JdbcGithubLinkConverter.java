package ru.tinkoff.edu.java.scrapper.jdbc.util.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.dto.entity.GithubLink;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.inteface.DateUtil;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JdbcGithubLinkConverter {
    public GithubLink makeGithubLink(String link, GitHubResponse gitHubResponse) {
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

    public Map<String, Object> makeGithubLinkTableQueryMap(GithubLink githubLink) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("link", githubLink.getLink());
        queryMap.put("repositoryId", githubLink.getRepositoryId());
        queryMap.put("name", githubLink.getName());
        queryMap.put("fullName", githubLink.getFullName());
        queryMap.put("lastActivityTime", githubLink.getLastActivityTime());
        queryMap.put("forksCount", githubLink.getForksCount());
        queryMap.put("openIssuesCount", githubLink.getOpenIssuesCount());
        queryMap.put("lastCheckTime", githubLink.getLastCheckTime());
        return queryMap;
    }
}
