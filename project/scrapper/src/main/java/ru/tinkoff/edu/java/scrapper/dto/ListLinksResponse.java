package ru.tinkoff.edu.java.scrapper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListLinksResponse {
    private ArrayList<LinkResponse> links;
    private int size;
}
