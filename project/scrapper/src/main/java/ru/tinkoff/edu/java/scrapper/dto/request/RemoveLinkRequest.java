package ru.tinkoff.edu.java.scrapper.dto.request;

import lombok.Data;

import java.net.URI;

@Data
public class RemoveLinkRequest {
    private URI link;
}
