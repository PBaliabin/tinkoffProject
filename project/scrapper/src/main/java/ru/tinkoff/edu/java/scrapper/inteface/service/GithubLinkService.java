package ru.tinkoff.edu.java.scrapper.inteface.service;


import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;

public interface GithubLinkService<T> {
    String OWNER = "owner";
    String REPOSITORY = "repository";

    void add(URI url);

    void remove(URI url);

    void update(T entityRecord);

    Collection<T> getLinksByLastCheckTime(LocalDateTime threshold);
}
