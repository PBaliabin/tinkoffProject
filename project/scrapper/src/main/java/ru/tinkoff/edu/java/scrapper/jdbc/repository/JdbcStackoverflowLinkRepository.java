package ru.tinkoff.edu.java.scrapper.jdbc.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.tinkoff.edu.java.scrapper.dto.entity.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.jdbc.mapper.StackoverflowLinkRowMapper;
import ru.tinkoff.edu.java.scrapper.jdbc.util.JdbcTypeConverter;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JdbcStackoverflowLinkRepository {

    private final NamedParameterJdbcTemplate namedParamJdbcTemplate;
    private final JdbcTypeConverter jdbcTypeConverter;

    public int add(StackoverflowLink stackoverflowLink) {
        String sqlQuery = "INSERT INTO github_link VALUES(" +
                ":link," +
                ":repositoryId," +
                ":name," +
                ":fullName," +
                ":lastActivityTime," +
                ":forksCount," +
                ":openIssuesCount," +
                ":lastCheckTime)";
        return namedParamJdbcTemplate.update(sqlQuery, jdbcTypeConverter.makeStackoverflowTableQueryMap(stackoverflowLink));
    }

    public int update(StackoverflowLink stackoverflowLink) {
        String sqlQuery = "UPDATE stackoverflow_link SET " +
                "quota_max = :quotaMax," +
                "quota_remaining = :quotaRemaining," +
                "last_activity_time = :lastActivityTime," +
                "is_answered = :isAnswered," +
                "answer_count = :answerCount," +
                "last_check_time = :lastCheckTime" +
                " WHERE link = :link";
        return namedParamJdbcTemplate.update(sqlQuery, jdbcTypeConverter.makeStackoverflowTableQueryMap(stackoverflowLink));
    }

    public int remove(URI url) {
        String sqlQuery = "DELETE FROM stackoverflow_link WHERE link = :link";
        Map<String, Object> map = new HashMap<>();
        map.put("link", url.toString());
        return namedParamJdbcTemplate.update(sqlQuery, map);
    }

    public List<StackoverflowLink> getAll() {
        String sqlQuery = "SELECT * FROM stackoverflow_link";
        return namedParamJdbcTemplate.query(sqlQuery, new StackoverflowLinkRowMapper());
    }

    public List<StackoverflowLink> getLinksBeforeLastCheckTime(LocalDateTime threshold) {
        String sqlQuery = "SELECT * FROM stackoverflow_link WHERE last_check_time < :threshold";
        Map<String, Object> map = new HashMap<>();
        map.put("threshold", threshold);
        return namedParamJdbcTemplate.query(sqlQuery, map, new StackoverflowLinkRowMapper());
    }
}
