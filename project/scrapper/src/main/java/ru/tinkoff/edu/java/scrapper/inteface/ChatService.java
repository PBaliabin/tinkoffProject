package ru.tinkoff.edu.java.scrapper.inteface;

import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatRecord;

import java.util.Collection;

public interface ChatService {
    void register(String tgChatId);

    void unregister(String tgChatId);

    Collection<ChatRecord> getAll();
}
