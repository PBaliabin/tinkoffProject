package ru.tinkoff.edu.java.scrapper.inteface;

import java.time.LocalDateTime;

public interface LinkUpdater {
    int update(LocalDateTime checkTimeThreshold);
}
