package ru.tinkoff.edu.java.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.inteface.LinkUpdater;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {

    private final LinkUpdater linkUpdater;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() throws InterruptedException {
        System.out.println("Starting update links");
//        linkUpdater.update(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1));
        linkUpdater.update(System.currentTimeMillis());
        System.out.println(TimeUnit.DAYS.toMillis(1));
        System.out.println("Links updated");
    }
}
