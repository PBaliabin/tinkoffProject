package ru.tinkoff.edu.java.scrapper.inteface.service;

import ru.tinkoff.edu.java.scrapper.dto.LinkUpdate;


public interface MessageService {
    void send(LinkUpdate linkUpdate);
}
