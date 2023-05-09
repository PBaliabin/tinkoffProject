package ru.tinkoff.edu.java.scrapper.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.inteface.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.inteface.service.MessageService;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {

    private final LinkUpdater linkUpdater;
    private final MessageService messageService;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() throws InterruptedException {
        log.info("Starting update links");
//        linkUpdater.update(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1));
        List<LinkUpdate> linkUpdateList =
                (List<LinkUpdate>) linkUpdater.update(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        for (LinkUpdate linkUpdate : linkUpdateList) {
            messageService.send(linkUpdate);
        }
        log.info("Links updated");
    }
}
