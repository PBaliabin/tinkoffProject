package ru.tinkoff.edu.java.scrapper.jooq.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.ChatToLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatToLinkRecord;
import ru.tinkoff.edu.java.scrapper.inteface.service.ChatToLinkService;
import ru.tinkoff.edu.java.scrapper.jooq.repository.JooqChatToLinkRepository;

import java.net.URI;
import java.util.Collection;

@RequiredArgsConstructor
public class JooqChatToLinkService implements ChatToLinkService<ChatToLinkRecord> {

    private final JooqChatToLinkRepository jooqChatToLinkRepository;

    @Override
    public void add(long tgChatId, URI url) {
        jooqChatToLinkRepository.add(new ChatToLink(url.toString(), tgChatId));
    }

    @Override
    public void remove(long tgChatId, URI url) {
        jooqChatToLinkRepository.delete(new ChatToLink(url.toString(), tgChatId));
    }

    @Override
    public Collection<ChatToLinkRecord> getLinksById(long tgChatId) {
        return jooqChatToLinkRepository.getLinksById(tgChatId);
    }

    @Override
    public Collection<ChatToLinkRecord> getChatsByLink(String url) {
        return jooqChatToLinkRepository.getChatsByLink(url);
    }
}
