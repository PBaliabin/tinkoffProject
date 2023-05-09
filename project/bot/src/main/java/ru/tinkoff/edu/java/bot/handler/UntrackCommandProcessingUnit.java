package ru.tinkoff.edu.java.bot.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UntrackCommandProcessingUnit implements CommandProcessingUnit {

    @Override
    public String processCommand(String messageText) {
        if (!messageText.equals("/untrack")) {
            return null;
        }
        log.info("attempt use unfinished command \"/untrack\"");
        return "sorry, feature \"untrack\" is under construction";
    }
}
