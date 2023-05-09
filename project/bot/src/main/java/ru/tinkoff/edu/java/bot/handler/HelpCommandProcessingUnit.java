package ru.tinkoff.edu.java.bot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HelpCommandProcessingUnit implements CommandProcessingUnit {

    private final List<BotCommand> botCommandList;

    @Override
    public String processCommand(String messageText) {
        if (!messageText.equals("/help")) {
            return null;
        }

        return "Ниже описаны все команды, поддерживаемые данным ботом:\n\n"
                + this.getCommandListAsString();
    }

    private String getCommandListAsString() {
        List<BotCommand> botCommandList = this.botCommandList;
        StringBuilder stringBuilder = new StringBuilder();
        botCommandList.forEach(botCommand -> stringBuilder
                .append(botCommand.getCommand())
                .append(" - ")
                .append(botCommand.getDescription())
                .append("\n"));
        return stringBuilder.toString();
    }
}
