package ru.tinkoff.edu.java.scrapper.jpa.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.inteface.service.ChatService;
import ru.tinkoff.edu.java.scrapper.jpa.entity.Chat;
import ru.tinkoff.edu.java.scrapper.jpa.repository.JpaChatRepository;

import java.util.Collection;

@RequiredArgsConstructor
public class JpaChatService implements ChatService<Chat> {
    private final JpaChatRepository jpaChatRepository;

    @Override
    public void register(long tgChatId) {
        Chat chatToRegister = new Chat(tgChatId);
        jpaChatRepository.save(chatToRegister);
    }

    @Override
    public void unregister(long tgChatId) {
        Chat chatToRegister = new Chat(tgChatId);
        jpaChatRepository.delete(chatToRegister);
    }

    @Override
    public Collection<Chat> getAll() {
        return (Collection<Chat>) jpaChatRepository.findAll();
    }
}
