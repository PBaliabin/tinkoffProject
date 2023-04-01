package ru.tinkoff.edu.java.scrapper;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LinkUpdaterScheduler {

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() throws InterruptedException {
        System.out.println("here logs starts");
        Thread.sleep(3000);
        System.out.println("here logs ends");
    }
}
