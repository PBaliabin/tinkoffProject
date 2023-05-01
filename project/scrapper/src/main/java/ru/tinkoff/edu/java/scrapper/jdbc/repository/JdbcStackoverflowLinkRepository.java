package ru.tinkoff.edu.java.scrapper.jdbc.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.tinkoff.edu.java.scrapper.dto.entity.StackoverflowLink;
import ru.tinkoff.edu.java.scrapper.jdbc.mapper.StackoverflowLinkRowMapper;
import ru.tinkoff.edu.java.scrapper.jdbc.util.converter.JdbcStackoverflowLinkConverter;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JdbcStackoverflowLinkRepository {

    private final NamedParameterJdbcTemplate namedParamJdbcTemplate;
    private final JdbcStackoverflowLinkConverter jdbcStackoverflowLinkConverter;

    public void add(StackoverflowLink stackoverflowLink) {
        String sqlQuery = "INSERT INTO stackoverflow_link VALUES(" +
                ":link," +
                ":quotaMax," +
                ":quotaRemaining," +
                ":lastActivityTime," +
                ":isAnswered," +
                ":answerCount," +
                ":lastCheckTime)";
        namedParamJdbcTemplate.update(sqlQuery, jdbcStackoverflowLinkConverter.makeStackoverflowTableQueryMap(stackoverflowLink));
    }

    public void update(StackoverflowLink stackoverflowLink) {
        String sqlQuery = "UPDATE stackoverflow_link SET " +
                "quota_max = :quotaMax," +
                "quota_remaining = :quotaRemaining," +
                "last_activity_time = :lastActivityTime," +
                "is_answered = :isAnswered," +
                "answer_count = :answerCount," +
                "last_check_time = :lastCheckTime" +
                " WHERE link = :link";
        namedParamJdbcTemplate.update(sqlQuery, jdbcStackoverflowLinkConverter.makeStackoverflowTableQueryMap(stackoverflowLink));
    }

    public void remove(URI url) {
        String sqlQuery = "DELETE FROM stackoverflow_link WHERE link = :link";
        Map<String, Object> map = new HashMap<>();
        map.put("link", url.toString());
        namedParamJdbcTemplate.update(sqlQuery, map);
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
