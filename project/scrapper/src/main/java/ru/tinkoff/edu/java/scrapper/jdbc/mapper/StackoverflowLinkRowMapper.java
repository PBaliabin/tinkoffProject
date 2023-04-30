package ru.tinkoff.edu.java.scrapper.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.dto.entity.StackoverflowLink;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StackoverflowLinkRowMapper implements RowMapper<StackoverflowLink> {

    @Override
    public StackoverflowLink mapRow(ResultSet rs, int rowNum) throws SQLException {
        StackoverflowLink link = new StackoverflowLink();
        link.setLink(rs.getString("link"));
        link.setQuotaMax(rs.getInt("quota_max"));
        link.setQuotaRemaining(rs.getInt("quota_remaining"));
        link.setLastActivityTime(rs.getTimestamp("last_activity_time").toLocalDateTime());
        link.setIsAnswered(rs.getBoolean("is_answered"));
        link.setAnswerCount(rs.getInt("answer_count"));
        link.setLastCheckTime(rs.getTimestamp("last_check_time").toLocalDateTime());
        return link;
    }
}
