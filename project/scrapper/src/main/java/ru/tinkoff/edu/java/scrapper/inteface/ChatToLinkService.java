package ru.tinkoff.edu.java.scrapper.inteface;


import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.ChatToLink;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatToLinkRecord;

import java.net.URI;
import java.util.Collection;

public interface ChatToLinkService {
    int add(long tgChatId, URI url);

    int remove(long tgChatId, URI url);

    int update(ChatToLink row);

    Collection<ChatToLinkRecord> getLinksById(long tgChatId);

    Collection<ChatToLinkRecord> getChatsByLink(String url);
}
