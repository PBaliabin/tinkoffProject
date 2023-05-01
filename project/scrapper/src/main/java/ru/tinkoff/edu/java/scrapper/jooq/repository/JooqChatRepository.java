package ru.tinkoff.edu.java.scrapper.jooq.repository;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatRecord;

import java.util.Collection;

@AllArgsConstructor
public class JooqChatRepository {
    private final DSLContext dslContext;
    private final Chat chatTable;

    public void register(long tgChatId) {
        dslContext.insertInto(chatTable).values(tgChatId).execute();
    }

    public void unregister(long tgChatId) {
        dslContext.deleteFrom(chatTable).where(chatTable.CHAT_ID.eq(tgChatId)).execute();
    }

    public Collection<ChatRecord> getAll() {
        return dslContext.selectFrom(chatTable).fetch();
    }
}
