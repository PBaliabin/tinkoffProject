package ru.tinkoff.edu.java.bot.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TrackCommandProcessingUnit implements CommandProcessingUnit {

    @Override
    public String processCommand(String messageText) {
        if (!messageText.equals("/track")) {
            return null;
        }
        log.info("attempt use unfinished command \"/track\"");
        return "sorry, feature \"track\" is under construction";
    }
}
