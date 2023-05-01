package ru.tinkoff.edu.java.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.inteface.LinkUpdater;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {

    private final LinkUpdater linkUpdater;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() throws InterruptedException {
        System.out.println("Starting update links");
//        linkUpdater.update(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1));
//        linkUpdater.update(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        System.out.println("Links updated");
    }
}
