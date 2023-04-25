package ru.tinkoff.edu.java.scrapper.jooq.service;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatRecord;
import ru.tinkoff.edu.java.scrapper.inteface.service.ChatService;

import java.util.Collection;

@RequiredArgsConstructor
public class JooqChatService implements ChatService<ChatRecord> {

    private final DSLContext dslContext;
    private final ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat chatTable;

    @Override
    public void register(long tgChatId) {
        dslContext.insertInto(chatTable).values(new Chat(tgChatId)).execute();
    }

    @Override
    public void unregister(long tgChatId) {
        dslContext.deleteFrom(chatTable).where(chatTable.CHAT_ID.eq(tgChatId)).execute();
    }

    @Override
    public Collection<ChatRecord> getAll() {
        return dslContext
                .selectFrom(chatTable)
                .fetch();
    }
}
