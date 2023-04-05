package ru.tinkoff.edu.java.bot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListLinksResponse {
    private List<LinkResponse> links;
    private int size;
}
