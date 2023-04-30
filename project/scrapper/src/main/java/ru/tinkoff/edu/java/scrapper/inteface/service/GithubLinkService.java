package ru.tinkoff.edu.java.scrapper.inteface.service;


import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;

public interface GithubLinkService<T1> {
    String OWNER = "owner";
    String REPOSITORY = "repository";

    void add(URI url);

    void remove(URI url);

    void update(T1 entityRecord);

    Collection<T1> getLinksByLastCheckTime(LocalDateTime threshold);
}
