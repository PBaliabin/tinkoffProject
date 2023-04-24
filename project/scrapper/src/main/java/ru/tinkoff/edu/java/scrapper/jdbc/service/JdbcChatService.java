package ru.tinkoff.edu.java.scrapper.jdbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.entity.Chat;
import ru.tinkoff.edu.java.scrapper.inteface.service.ChatService;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcChatRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class JdbcChatService implements ChatService<Chat> {
    private final JdbcChatRepository jdbcChatRepository;

    @Override
    public void register(long tgChatId) {
        Chat chatToRegister = new Chat(tgChatId);
        jdbcChatRepository.register(chatToRegister);
    }

    @Override
    public void unregister(long tgChatId) {
        Chat chatToRegister = new Chat(tgChatId);
        jdbcChatRepository.unregister(chatToRegister);
    }

    @Override
    public Collection<Chat> getAll() {
        return jdbcChatRepository.getAll();
    }
}
