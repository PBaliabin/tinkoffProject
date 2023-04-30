package ru.tinkoff.edu.java.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.LinkChangeLog;
import ru.tinkoff.edu.java.scrapper.inteface.client.TgBotClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TgBotClientService {

    private final TgBotClient tgBotClient;

    public void sendUpdates(List<LinkChangeLog> linkChangeLogList) {
        tgBotClient.sendUpdates(linkChangeLogList);
    }
}
