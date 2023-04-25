package ru.tinkoff.edu.java.scrapper.jdbc.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.tinkoff.edu.java.scrapper.dto.entity.GithubLink;
import ru.tinkoff.edu.java.scrapper.jdbc.mapper.GithubLinkRowMapper;
import ru.tinkoff.edu.java.scrapper.jdbc.util.JdbcTypeConverter;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JdbcGithubLinkRepository {

    private final NamedParameterJdbcTemplate namedParamJdbcTemplate;
    private final JdbcTypeConverter jdbcTypeConverter;

    public int add(GithubLink githubLink) {
        String sqlQuery = "INSERT INTO github_link VALUES(" +
                ":link," +
                ":repositoryId," +
                ":name," +
                ":fullName," +
                ":lastActivityTime," +
                ":forksCount," +
                ":openIssuesCount," +
                ":lastCheckTime)";
        return namedParamJdbcTemplate.update(sqlQuery, jdbcTypeConverter.makeGithubLinkTableQueryMap(githubLink));
    }

    public int update(GithubLink githubLink) {
        String sqlQuery = "UPDATE github_link SET " +
                "repository_id = :repositoryId," +
                "name = :name," +
                "full_name = :fullName," +
                "last_activity_time = :lastActivityTime," +
                "forks_count = :forksCount," +
                "open_issues_count = :openIssuesCount," +
                "last_check_time = :lastCheckTime" +
                " WHERE link = :link";
        return namedParamJdbcTemplate.update(sqlQuery, jdbcTypeConverter.makeGithubLinkTableQueryMap(githubLink));
    }

    public int remove(URI url) {
        String sqlQuery = "DELETE FROM github_link WHERE link = :link";
        Map<String, Object> map = new HashMap<>();
        map.put("link", url.toString());
        return namedParamJdbcTemplate.update(sqlQuery, map);
    }

    public List<GithubLink> getAll() {
        String sqlQuery = "SELECT * FROM github_link";
        return namedParamJdbcTemplate.query(sqlQuery, new GithubLinkRowMapper());
    }

    public List<GithubLink> getLinksBeforeLastCheckTime(LocalDateTime threshold) {
        String sqlQuery = "SELECT * FROM github_link WHERE last_check_time < :threshold";
        Map<String, Object> map = new HashMap<>();
        map.put("threshold", threshold);
        return namedParamJdbcTemplate.query(sqlQuery, map, new GithubLinkRowMapper());
    }
}
