package ru.tinkoff.edu.java.scrapper.jooq.service;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.ChatToLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatToLinkRecord;
import ru.tinkoff.edu.java.scrapper.inteface.service.ChatToLinkService;

import java.net.URI;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class JooqChatToLinkService implements ChatToLinkService<ChatToLinkRecord> {

    private final DSLContext dslContext;
    private final ru.tinkoff.edu.java.scrapper.domain.jooq.tables.ChatToLink chatToLinkTable;

    @Override
    public int add(long tgChatId, URI url) {
        return dslContext.insertInto(chatToLinkTable).values(new ChatToLink(url.toString(), tgChatId)).execute();
    }

    @Override
    public int remove(long tgChatId, URI url) {
        return dslContext
                .deleteFrom(chatToLinkTable)
                .where(chatToLinkTable.LINK.eq(url.toString())
                        .and(chatToLinkTable.CHAT_ID.eq(tgChatId)))
                .execute();
    }

    @Override
    public Collection<ChatToLinkRecord> getLinksById(long tgChatId) {
        return dslContext.selectFrom(chatToLinkTable).where(chatToLinkTable.CHAT_ID.eq(tgChatId)).fetch();
    }

    @Override
    public Collection<ChatToLinkRecord> getChatsByLink(String url) {
        return dslContext.selectFrom(chatToLinkTable).where(chatToLinkTable.LINK.eq(url)).fetch();
    }
}
