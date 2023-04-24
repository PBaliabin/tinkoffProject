package ru.tinkoff.edu.java.scrapper.inteface.client;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import ru.tinkoff.edu.java.scrapper.dto.LinkChangeLog;

import java.util.List;

public interface TgBotClient {
    @PostExchange("/update")
    void sendUpdates(@RequestBody List<LinkChangeLog> linkChangeLogs);
}
