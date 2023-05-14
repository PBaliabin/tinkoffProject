package ru.tinkoff.edu.java.scrapper.jooq.repository;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.ChatToLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatToLinkRecord;

import java.util.Collection;

@AllArgsConstructor
public class JooqChatToLinkRepository {
    private final DSLContext dslContext;
    private final ru.tinkoff.edu.java.scrapper.domain.jooq.tables.ChatToLink chatToLinkTable;

    public void add(ChatToLink chatToLink) {
        dslContext
                .insertInto(chatToLinkTable)
                .values(
                        chatToLink.getLink(),
                        chatToLink.getChatId())
                .execute();
    }

    public void delete(ChatToLink chatToLink) {
        dslContext
                .deleteFrom(chatToLinkTable)
                .where(chatToLinkTable.LINK.eq(chatToLink.getLink())
                                           .and(chatToLinkTable.CHAT_ID.eq(chatToLink.getChatId())))
                .execute();
    }

    public Collection<ChatToLinkRecord> getLinksById(long tgChatId) {
        return dslContext.selectFrom(chatToLinkTable).where(chatToLinkTable.CHAT_ID.eq(tgChatId)).fetch();
    }

    public Collection<ChatToLinkRecord> getChatsByLink(String url) {
        return dslContext.selectFrom(chatToLinkTable).where(chatToLinkTable.LINK.eq(url)).fetch();
    }
}
