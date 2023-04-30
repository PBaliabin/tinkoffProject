package ru.tinkoff.edu.java.scrapper.inteface.service;


import java.net.URI;
import java.util.Collection;

public interface ChatToLinkService<T> {
    void add(long tgChatId, URI url);

    void remove(long tgChatId, URI url);

    Collection<T> getLinksById(long tgChatId);

    Collection<T> getChatsByLink(String url);
}
