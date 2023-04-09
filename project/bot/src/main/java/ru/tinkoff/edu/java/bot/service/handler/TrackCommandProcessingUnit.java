package ru.tinkoff.edu.java.bot.service.handler;

import org.springframework.stereotype.Service;

@Service
public class TrackCommandProcessingUnit implements CommandProcessingUnit {

    @Override
    public String processCommand(String messageText) {
        if (!messageText.equals("/track")) {
            return null;
        }
        System.out.println("attempt use unfinished command \"/track\"");
        return "sorry, feature \"track\" is under construction";
    }
}
