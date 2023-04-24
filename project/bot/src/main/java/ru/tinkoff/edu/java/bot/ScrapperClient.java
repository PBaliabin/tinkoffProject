package ru.tinkoff.edu.java.bot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import ru.tinkoff.edu.java.bot.model.request.AddLinkRequest;
import ru.tinkoff.edu.java.bot.model.response.LinkResponse;
import ru.tinkoff.edu.java.bot.model.response.ListLinksResponse;
import ru.tinkoff.edu.java.bot.model.request.RemoveLinkRequest;

public interface ScrapperClient {

    @GetExchange("/links")
    ListLinksResponse getAllLinks(@RequestHeader(value = "Tg-Chat-Id") long tgChatId);

    @PostExchange("/links")
    LinkResponse addLink(@RequestBody AddLinkRequest link, @RequestHeader(value = "Tg-Chat-Id") long tgChatId);

    @DeleteExchange("/links")
    LinkResponse removeLink(@RequestBody RemoveLinkRequest link, @RequestHeader(value = "Tg-Chat-Id") long tgChatId);

    @PostExchange("/tg-chat/{id}")
    ResponseEntity<Void> signInChat(@PathVariable long id);

    @DeleteExchange("/tg-chat/{id}")
    ResponseEntity<Void> deleteChat(@PathVariable long id);
}
