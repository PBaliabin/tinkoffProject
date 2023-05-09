package ru.tinkoff.edu.java.bot.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StartCommandProcessingUnit implements CommandProcessingUnit {

    @Override
    public String processCommand(String messageText) {
        if (!messageText.equals("/start")) {
            return null;
        }
        log.info("attempt use unfinished command \"/start\"");
        return "sorry, feature \"user registration\" is under construction";
    }
}
