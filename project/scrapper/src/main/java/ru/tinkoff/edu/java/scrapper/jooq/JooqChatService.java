package ru.tinkoff.edu.java.scrapper.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatRecord;
import ru.tinkoff.edu.java.scrapper.inteface.ChatService;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class JooqChatService implements ChatService {

    private final DSLContext dslContext;
    private final ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat chatTable;

    @Override
    public void register(String tgChatId) {
        dslContext.insertInto(chatTable).values(new Chat(tgChatId)).execute();
    }

    @Override
    public void unregister(String tgChatId) {
        dslContext.deleteFrom(chatTable).where(chatTable.CHAT_ID.eq(tgChatId)).execute();
    }

    @Override
    public Collection<ChatRecord> getAll() {
        return dslContext
                .selectFrom(chatTable)
                .fetch();
    }
}
