package ru.tinkoff.edu.java.scrapper.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.dto.entity.GithubLink;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GithubLinkRowMapper implements RowMapper<GithubLink> {

    @Override
    public GithubLink mapRow(ResultSet rs, int rowNum) throws SQLException {
        GithubLink link = new GithubLink();
        link.setLink(rs.getString("link"));
        link.setRepositoryId(rs.getString("repository_id"));
        link.setName(rs.getString("name"));
        link.setFullName(rs.getString("full_name"));
        link.setLastActivityTime(rs.getTimestamp("last_activity_time").toLocalDateTime());
        link.setForksCount(rs.getInt("forks_count"));
        link.setOpenIssuesCount(rs.getInt("open_issues_count"));
        link.setLastCheckTime(rs.getTimestamp("last_check_time").toLocalDateTime());
        return link;
    }
}
