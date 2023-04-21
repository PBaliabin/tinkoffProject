package ru.tinkoff.edu.java.scrapper.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.dto.Chat;
import ru.tinkoff.edu.java.scrapper.dto.ChatToLink;
import ru.tinkoff.edu.java.scrapper.dto.Link;
import ru.tinkoff.edu.java.scrapper.mapper.ChatToLinkRowMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcChatToLinkRepository {

    @Autowired
    @Qualifier("customJdbcTemplate")
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;

    public int addRow(Chat chat, Link link) {
        String sqlQuery = "INSERT INTO chat_to_link VALUES(:link, :chatId)";
        Map<String, Object> map = new HashMap<>();
        map.put("link", link.getLink());
        map.put("chatId", chat.getChatId());
        return namedParamJdbcTemplate.update(sqlQuery, map);
    }

    public int deleteRow(Chat chat, Link link) {
        String sqlQuery = "DELETE FROM chat_to_link WHERE link = :link AND chat_id = :chatId";
        Map<String, Object> map = new HashMap<>();
        map.put("link", link.getLink());
        map.put("chatId", chat.getChatId());
        return namedParamJdbcTemplate.update(sqlQuery, map);
    }

    public List<ChatToLink> getAllLinksByChat(Chat chat) {
        String sqlQuery = "SELECT *  FROM chat_to_link WHERE chat_id = :chatId";
        Map<String, Object> map = new HashMap<>();
        map.put("chatId", chat.getChatId());
        return namedParamJdbcTemplate.query(sqlQuery, map, new ChatToLinkRowMapper());
    }

    public List<ChatToLink> getAllChatsByLink(Link link) {
        String sqlQuery = "SELECT *  FROM chat_to_link WHERE link = :link";
        Map<String, Object> map = new HashMap<>();
        map.put("link", link.getLink());
        return namedParamJdbcTemplate.query(sqlQuery, map, new ChatToLinkRowMapper());
    }
}
