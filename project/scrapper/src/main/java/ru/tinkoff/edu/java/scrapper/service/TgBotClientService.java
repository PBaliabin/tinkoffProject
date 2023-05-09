package ru.tinkoff.edu.java.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.inteface.client.TgBotClient;
import ru.tinkoff.edu.java.scrapper.inteface.service.MessageService;

import java.util.List;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class TgBotClientService implements MessageService {

    private final TgBotClient tgBotClient;

    @Override
    public void send(LinkUpdate linkUpdate) {
        tgBotClient.sendUpdates(linkUpdate);
    }
}
