package ru.tinkoff.edu.java.scrapper.inteface.service;


import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;

public interface StackoverflowLinkService<T1> {
    String QUESTION_ID = "questionId";
    String SITE_STACKOVERFLOW = "stackoverflow";

    int add(URI url);

    int remove(URI url);

    int update(T1 entityRecord);

    Collection<T1> getLinksByLastCheckTime(LocalDateTime threshold);
}
