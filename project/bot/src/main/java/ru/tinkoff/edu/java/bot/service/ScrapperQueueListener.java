package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.tinkoff.edu.java.bot.model.dto.LinkUpdate;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RabbitListener(queues = "${app.rabbit-configuration-parameters.queue-name}")
public class ScrapperQueueListener {
    private final UpdateProcessService updateProcessService;

    @RabbitHandler
    public void receiver(LinkUpdate linkUpdate) {
        Map<String, String> processedUpdate = updateProcessService.processUpdate(linkUpdate);
        String logMessage = "\nTelegram chat with id=" +
                processedUpdate.get(updateProcessService.TG_CHAT_ID) +
                " has following updates:\n" +
                processedUpdate.get(updateProcessService.MESSAGE);
        log.info(logMessage);
    }
}
