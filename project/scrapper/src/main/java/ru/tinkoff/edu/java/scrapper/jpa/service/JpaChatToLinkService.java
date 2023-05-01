package ru.tinkoff.edu.java.scrapper.jpa.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.inteface.service.ChatToLinkService;
import ru.tinkoff.edu.java.scrapper.jpa.entity.ChatToLink;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaChatToLinkRepository;

import java.net.URI;
import java.util.Collection;

@RequiredArgsConstructor
public class JpaChatToLinkService implements ChatToLinkService<ChatToLink> {
    private final JpaChatToLinkRepository jpaChatToLinkRepository;

    @Override
    public void add(long tgChatId, URI url) {
        jpaChatToLinkRepository.save(new ChatToLink(url.toString(), tgChatId));
    }

    @Override
    public void remove(long tgChatId, URI url) {
        jpaChatToLinkRepository.delete(new ChatToLink(url.toString(), tgChatId));
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
