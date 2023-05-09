package ru.tinkoff.edu.java.scrapper.inteface.service;


import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;

public interface StackoverflowLinkService<T> {
    String QUESTION_ID = "questionId";
    String SITE_STACKOVERFLOW = "stackoverflow";

    void add(URI url);

    void remove(URI url);

    void update(T entityRecord);

    Collection<T> getLinksByLastCheckTime(LocalDateTime threshold);
}
