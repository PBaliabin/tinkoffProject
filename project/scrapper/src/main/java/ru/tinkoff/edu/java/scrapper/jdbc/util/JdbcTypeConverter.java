package ru.tinkoff.edu.java.scrapper.jdbc.util;

import ru.tinkoff.edu.java.scrapper.dto.entity.GithubLink;
import ru.tinkoff.edu.java.scrapper.dto.entity.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.dto.response.GitHubResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.inteface.DateUtil;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class JdbcTypeConverter {
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

    public Map<String, Object> makeStackoverflowTableQueryMap(StackoverflowLink stackoverflowLink) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("link", stackoverflowLink.getLink());
        queryMap.put("quotaMax", stackoverflowLink.getQuotaMax());
        queryMap.put("quotaRemaining", stackoverflowLink.getQuotaRemaining());
        queryMap.put("lastActivityTime", stackoverflowLink.getLastActivityTime());
        queryMap.put("isAnswered", stackoverflowLink.getIsAnswered());
        queryMap.put("answerCount", stackoverflowLink.getAnswerCount());
        queryMap.put("lastCheckTime", stackoverflowLink.getLastCheckTime());
        return queryMap;
    }
}
