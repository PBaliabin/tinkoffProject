package ru.tinkoff.edu.java.bot.handler;

import org.springframework.stereotype.Service;

@Service
public class UntrackCommandProcessingUnit implements CommandProcessingUnit {

    @Override
    public String processCommand(String messageText) {
        if (!messageText.equals("/untrack")) {
            return null;
        }
        System.out.println("attempt use unfinished command \"/untrack\"");
        return "sorry, feature \"untrack\" is under construction";
    }
}
