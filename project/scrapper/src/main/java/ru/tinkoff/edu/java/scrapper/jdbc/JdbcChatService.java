package ru.tinkoff.edu.java.scrapper.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.Chat;
import ru.tinkoff.edu.java.scrapper.inteface.TgChatService;
import ru.tinkoff.edu.java.scrapper.repository.JdbcChatRepository;

@Service
@RequiredArgsConstructor
public class JdbcChatService implements TgChatService {
    private final JdbcChatRepository jdbcChatRepository;

    @Override
    public void register(long tgChatId) {
        Chat chatToRegister = new Chat(String.valueOf(tgChatId));
        jdbcChatRepository.addChat(chatToRegister);
    }

    @Override
    public void unregister(long tgChatId) {
        Chat chatToRegister = new Chat(String.valueOf(tgChatId));
        jdbcChatRepository.deleteChat(chatToRegister);
    }
}
