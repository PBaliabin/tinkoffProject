package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.service.handler.CommandProcessingUnit;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TelegramBotService {

    private final List<CommandProcessingUnit> commandProcessingUnits;

    public String processMessage(String messageText) {
        return this.commandProcessingUnits
                .stream()
                .map(commandProcessingUnit -> commandProcessingUnit.processCommand(messageText))
                .filter(Objects::nonNull)
                .findFirst().orElse("Извините, данная команда не поддерживается");
    }
}
