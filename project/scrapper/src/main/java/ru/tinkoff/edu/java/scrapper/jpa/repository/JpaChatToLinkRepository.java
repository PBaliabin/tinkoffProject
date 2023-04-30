package ru.tinkoff.edu.java.scrapper.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tinkoff.edu.java.scrapper.jpa.entity.ChatToLink;
import ru.tinkoff.edu.java.scrapper.jpa.entity.ChatToLinkId;

import java.util.Collection;

public interface JpaChatToLinkRepository extends CrudRepository<ChatToLink, ChatToLinkId> {
    Collection<ChatToLink> getAllByChatId(long tgChatId);

    Collection<ChatToLink> getAllByLink(String tgChatId);
}
