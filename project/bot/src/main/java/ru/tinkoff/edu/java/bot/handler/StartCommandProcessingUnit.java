package ru.tinkoff.edu.java.bot.handler;

import org.springframework.stereotype.Service;

@Service
public class StartCommandProcessingUnit implements CommandProcessingUnit {

    @Override
    public String processCommand(String messageText) {
        if (!messageText.equals("/start")) {
            return null;
        }
        System.out.println("attempt use unfinished command \"/start\"");
        return "sorry, feature \"user registration\" is under construction";
    }
}
