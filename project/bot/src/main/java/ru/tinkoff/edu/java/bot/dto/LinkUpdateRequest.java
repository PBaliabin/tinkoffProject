package ru.tinkoff.edu.java.bot.dto;

import lombok.Data;

import java.net.URI;
import java.util.ArrayList;

@Data
public class LinkUpdateRequest {
    private long id;
    private URI url;
    private String description;
    private ArrayList<Long> tgChatIds;
}
