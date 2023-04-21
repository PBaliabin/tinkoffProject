package ru.tinkoff.edu.java.scrapper.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.dto.Link;
import ru.tinkoff.edu.java.scrapper.mapper.LinkRowMapper;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcLinkRepository {

    @Autowired
    @Qualifier("customJdbcTemplate")
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;

    public int addLink(Link link) {
        String sqlQuery = "INSERT INTO link VALUES(:link, :lastActivityTime, :lastCheckTime)";
        Map<String, Object> map = new HashMap<>();
        map.put("link", link.getLink());
        map.put("lastActivityTime", link.getLastActivityTime());
        map.put("lastCheckTime", link.getLastCheckTime());
        return namedParamJdbcTemplate.update(sqlQuery, map);
    }

    public int updateLink(Link link) {
        String sqlQuery = "UPDATE link SET last_activity_time = :lastActivityTime, last_check_time = :lastCheckTime WHERE link = :link";
        Map<String, Object> map = new HashMap<>();
        map.put("link", link.getLink());
        map.put("lastActivityTime", link.getLastActivityTime());
        map.put("lastCheckTime", link.getLastCheckTime());
        return namedParamJdbcTemplate.update(sqlQuery, map);
    }

    public int deleteLink(Link link) {
        String sqlQuery = "DELETE FROM link WHERE link = :link";
        Map<String, Object> map = new HashMap<>();
        map.put("link", link.getLink());
        return namedParamJdbcTemplate.update(sqlQuery, map);
    }

    public List<Link> getAllLinks() {
        String sqlQuery = "SELECT * FROM link";
        return namedParamJdbcTemplate.query(sqlQuery, new LinkRowMapper());
    }

    public List<Link> getLinksBeforeLastCheckTime(Timestamp checkTime) {
        String sqlQuery = "SELECT * FROM link WHERE last_check_time < :lastCheckTime";
        Map<String, Object> map = new HashMap<>();
        map.put("lastCheckTime", checkTime);
        return namedParamJdbcTemplate.query(sqlQuery, map, new LinkRowMapper());
    }
}
