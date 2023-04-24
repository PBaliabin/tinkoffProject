package ru.tinkoff.edu.java.bot.model.request;

import lombok.Data;

import java.net.URI;

@Data
public class RemoveLinkRequest {
    private URI link;
}
