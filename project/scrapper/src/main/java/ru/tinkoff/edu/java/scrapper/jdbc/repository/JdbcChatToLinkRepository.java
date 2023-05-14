package ru.tinkoff.edu.java.scrapper.jdbc.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.ChatToLink;
import ru.tinkoff.edu.java.scrapper.jdbc.mapper.ChatToLinkRowMapper;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class JdbcChatToLinkRepository {

    private static final String LINK = "link";
    private static final String CHAT_ID = "chatId";


    private NamedParameterJdbcTemplate namedParamJdbcTemplate;

    public void addRow(Chat chat, URI url) {
        String sqlQuery = "INSERT INTO chat_to_link VALUES(:link, :chatId)";
        Map<String, Object> map = new HashMap<>();
        map.put(LINK, url.toString());
        map.put(CHAT_ID, chat.getChatId());
        namedParamJdbcTemplate.update(sqlQuery, map);
    }

    public void deleteRow(Chat chat, URI url) {
        String sqlQuery = "DELETE FROM chat_to_link WHERE link = :link AND chat_id = :chatId";
        Map<String, Object> map = new HashMap<>();
        map.put(LINK, url.toString());
        map.put(CHAT_ID, chat.getChatId());
        namedParamJdbcTemplate.update(sqlQuery, map);
    }

    public List<ChatToLink> getAllLinksByChat(Chat chat) {
        String sqlQuery = "SELECT *  FROM chat_to_link WHERE chat_id = :chatId";
        Map<String, Object> map = new HashMap<>();
        map.put(CHAT_ID, chat.getChatId());
        return namedParamJdbcTemplate.query(sqlQuery, map, new ChatToLinkRowMapper());
    }

    public List<ChatToLink> getAllChatsByLink(String link) {
        String sqlQuery = "SELECT *  FROM chat_to_link WHERE link = :link";
        Map<String, Object> map = new HashMap<>();
        map.put(LINK, link);
        return namedParamJdbcTemplate.query(sqlQuery, map, new ChatToLinkRowMapper());
    }
}
