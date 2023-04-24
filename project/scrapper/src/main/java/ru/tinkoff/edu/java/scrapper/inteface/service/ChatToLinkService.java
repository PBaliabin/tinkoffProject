package ru.tinkoff.edu.java.scrapper.inteface.service;


import java.net.URI;
import java.util.Collection;

public interface ChatToLinkService<T> {
    int add(long tgChatId, URI url);

    int remove(long tgChatId, URI url);

    Collection<T> getLinksById(long tgChatId);

    Collection<T> getChatsByLink(String url);
}
