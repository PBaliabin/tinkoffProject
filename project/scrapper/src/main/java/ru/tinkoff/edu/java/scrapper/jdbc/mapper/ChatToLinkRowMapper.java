package ru.tinkoff.edu.java.scrapper.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.dto.entity.ChatToLink;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatToLinkRowMapper implements RowMapper<ChatToLink> {

    @Override
    public ChatToLink mapRow(ResultSet rs, int rowNum) throws SQLException {
        ChatToLink chatToLink = new ChatToLink();
        chatToLink.setChatId(rs.getLong("chat_id"));
        chatToLink.setLink(rs.getString("link"));
        return chatToLink;
    }
}
