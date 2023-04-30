package ru.tinkoff.edu.java.scrapper.jooq.repository;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.ChatToLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatToLinkRecord;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class JooqChatToLinkRepository {
    private final DSLContext dslContext;
    private final ru.tinkoff.edu.java.scrapper.domain.jooq.tables.ChatToLink chatToLinkTable;

    public int add(ChatToLink chatToLink) {
        return dslContext
                .insertInto(chatToLinkTable)
                .values(
                        chatToLink.getLink(),
                        chatToLink.getChatId())
                .execute();
    }

    public int delete(ChatToLink chatToLink) {
        return dslContext
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
