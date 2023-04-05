package ru.tinkoff.edu.java.bot.model.dto;

import lombok.Data;

import java.net.URI;

@Data
public class AddLinkRequest {
    private URI link;
}
