package ru.tinkoff.edu.java.scrapper.jdbc.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.jdbc.mapper.ChatRowMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class JdbcChatRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void register(Chat chat) {
        String sqlQuery = "INSERT INTO chat VALUES(:chatId)";
        Map<String, Object> map = new HashMap<>();
        map.put("chatId", chat.getChatId());
        namedParameterJdbcTemplate.update(sqlQuery, map);
    }

    public void unregister(Chat chat) {
        String sqlQuery = "DELETE FROM chat WHERE chat_id = :chatId";
        Map<String, Object> map = new HashMap<>();
        map.put("chatId", chat.getChatId());
        namedParameterJdbcTemplate.update(sqlQuery, map);
    }

    public List<Chat> getAll() {
        String sqlQuery = "SELECT * FROM chat";
        return namedParameterJdbcTemplate.query(sqlQuery, new ChatRowMapper());
    }
}
