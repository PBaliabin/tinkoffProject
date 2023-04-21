package ru.tinkoff.edu.java.scrapper.inteface;

public interface TgChatService {
    void register(long tgChatId);

    void unregister(long tgChatId);
}
