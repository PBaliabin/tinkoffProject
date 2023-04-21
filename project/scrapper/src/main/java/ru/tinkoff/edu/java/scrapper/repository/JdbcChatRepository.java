package ru.tinkoff.edu.java.scrapper.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.dto.Chat;
import ru.tinkoff.edu.java.scrapper.mapper.ChatRowMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcChatRepository {

    @Autowired
    @Qualifier("customJdbcTemplate")
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public int addChat(Chat chat) {
        String sqlQuery = "INSERT INTO chat VALUES(:chatId)";
        Map<String, Object> map = new HashMap<>();
        map.put("chatId", chat.getChatId());
        return namedParameterJdbcTemplate.update(sqlQuery, map);
    }

    public int deleteChat(Chat chat) {
        String sqlQuery = "DELETE FROM chat WHERE chat_id = :chatId";
        Map<String, Object> map = new HashMap<>();
        map.put("chatId", chat.getChatId());
        return namedParameterJdbcTemplate.update(sqlQuery, map);
    }

    public List<Chat> getAllChats() {
        String sqlQuery = "SELECT * FROM chat";
        return namedParameterJdbcTemplate.query(sqlQuery, new ChatRowMapper());
    }
}
