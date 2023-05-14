package ru.tinkoff.edu.java.scrapper.inteface;

import ru.tinkoff.edu.java.scrapper.dto.LinkUpdate;

import java.time.LocalDateTime;
import java.util.Collection;

public interface LinkUpdater {
    Collection<LinkUpdate> update(LocalDateTime checkTimeThreshold);
}
