package ru.tinkoff.edu.java.scrapper.dto;

import lombok.Data;

import java.net.URI;

@Data
public class AddLinkRequest {
    private URI link;
}
