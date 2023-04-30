package ru.tinkoff.edu.java.scrapper.inteface.service;

import java.util.Collection;

public interface ChatService<T> {
    void register(long tgChatId);

    void unregister(long tgChatId);

    Collection<T> getAll();
}
