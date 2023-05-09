package ru.tinkoff.edu.java.scrapper.inteface.client;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdate;

public interface TgBotClient {
    @PostExchange("/update")
    void sendUpdates(@RequestBody LinkUpdate linkUpdates);
}
