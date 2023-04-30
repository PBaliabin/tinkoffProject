package ru.tinkoff.edu.java.scrapper.jdbc.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.dto.entity.ChatToLink;
import ru.tinkoff.edu.java.scrapper.inteface.service.ChatToLinkService;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcChatToLinkRepository;

import java.net.URI;
import java.util.Collection;

@RequiredArgsConstructor
public class JdbcChatToLinkService implements ChatToLinkService<ChatToLink> {
    private final JdbcChatToLinkRepository jdbcChatToLinkRepository;

    @Override
    public int add(long tgChatId, URI url) {
        return jdbcChatToLinkRepository.addRow(new Chat(tgChatId), url);
    }

    @Override
    public int remove(long tgChatId, URI url) {
        return jdbcChatToLinkRepository.deleteRow(new Chat(tgChatId), url);
    }

    @Override
    public Collection<ChatToLink> getLinksById(long tgChatId) {
        return jdbcChatToLinkRepository.getAllLinksByChat(new Chat(tgChatId));
    }

    @Override
    public Collection<ChatToLink> getChatsByLink(String url) {
        return jdbcChatToLinkRepository.getAllChatsByLink(url);
    }
}
