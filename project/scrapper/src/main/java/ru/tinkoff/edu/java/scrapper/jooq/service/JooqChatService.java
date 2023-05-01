package ru.tinkoff.edu.java.scrapper.jooq.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatRecord;
import ru.tinkoff.edu.java.scrapper.inteface.service.ChatService;
import ru.tinkoff.edu.java.scrapper.jooq.repository.JooqChatRepository;

import java.util.Collection;

@RequiredArgsConstructor
public class JooqChatService implements ChatService<ChatRecord> {

    private final JooqChatRepository jooqChatRepository;

    @Override
    public void register(long tgChatId) {
        jooqChatRepository.register(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        jooqChatRepository.unregister(tgChatId);
    }

    @Override
    public Collection<ChatRecord> getAll() {
        return jooqChatRepository.getAll();
    }
}
