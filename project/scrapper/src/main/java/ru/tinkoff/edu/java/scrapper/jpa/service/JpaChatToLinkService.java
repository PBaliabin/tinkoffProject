package ru.tinkoff.edu.java.scrapper.jpa.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.inteface.service.ChatToLinkService;
import ru.tinkoff.edu.java.scrapper.jpa.entity.ChatToLink;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaChatToLinkRepository;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class JpaChatToLinkService implements ChatToLinkService<ChatToLink> {
    private final JpaChatToLinkRepository jpaChatToLinkRepository;

    @Override
    public int add(long tgChatId, URI url) {
        return List.of(jpaChatToLinkRepository.save(new ChatToLink(url.toString(), tgChatId))).size();
    }

    @Override
    public int remove(long tgChatId, URI url) {
        jpaChatToLinkRepository.delete(new ChatToLink(url.toString(), tgChatId));
        return 1;
    }

    @Override
    public Collection<ChatToLink> getLinksById(long tgChatId) {
        return jpaChatToLinkRepository.getAllByChatId(tgChatId);
    }

    @Override
    public Collection<ChatToLink> getChatsByLink(String url) {
        return jpaChatToLinkRepository.getAllByLink(url);
    }
}
