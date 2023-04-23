package ru.tinkoff.edu.java.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.model.dto.AddLinkRequest;
import ru.tinkoff.edu.java.bot.model.dto.LinkResponse;
import ru.tinkoff.edu.java.bot.model.dto.ListLinksResponse;
import ru.tinkoff.edu.java.bot.model.dto.RemoveLinkRequest;

@Service
@RequiredArgsConstructor
public class ScrapperClientService {

    private final ScrapperClient scrapperClient;

    public ListLinksResponse getAllLinks(long tgChatId) {
        return scrapperClient.getAllLinks(tgChatId);
    }

    public LinkResponse addLink(AddLinkRequest link, long tgChatId) {
        return scrapperClient.addLink(link, tgChatId);
    }

    public LinkResponse removeLink(RemoveLinkRequest link, long tgChatId) {
        return scrapperClient.removeLink(link, tgChatId);
    }

    public ResponseEntity<Void> signInChat(long id) {
        return scrapperClient.signInChat(id);
    }

    public ResponseEntity<Void> deleteChat(long id) {
        return scrapperClient.deleteChat(id);
    }
}
