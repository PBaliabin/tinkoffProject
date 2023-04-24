package ru.tinkoff.edu.java.bot.model.request;

import lombok.Data;

import java.net.URI;
import java.util.List;

@Data
public class LinkUpdateRequest {
    private long id;
    private URI url;
    private String description;
    private List<Long> tgChatIds;
}
