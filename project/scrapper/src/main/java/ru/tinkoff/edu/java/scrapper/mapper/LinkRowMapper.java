package ru.tinkoff.edu.java.scrapper.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.dto.Link;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LinkRowMapper implements RowMapper<Link> {

    @Override
    public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
        Link link = new Link();
        link.setLink(rs.getString("link"));
        link.setLastActivityTime(rs.getTimestamp("last_activity_time"));
        link.setLastCheckTime(rs.getTimestamp("last_check_time"));
        return link;
    }
}
