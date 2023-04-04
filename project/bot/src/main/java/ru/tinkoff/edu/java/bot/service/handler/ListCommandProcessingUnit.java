package ru.tinkoff.edu.java.bot.service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListCommandProcessingUnit implements CommandProcessingUnit {
    private static final String EMPTY_LINK_LIST_MESSAGE = "Список отслеживаемых ссылок пуст";

    private final List<URI> linksList;

    @Override
    public String processCommand(String messageText) {
        if (!messageText.equals("/list")) {
            return null;
        }

        if (linksList.isEmpty()) {
            return EMPTY_LINK_LIST_MESSAGE;
        } else {
            return linksList.toString();
        }
    }
}
