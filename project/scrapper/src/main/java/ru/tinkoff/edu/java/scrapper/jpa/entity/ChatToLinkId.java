package ru.tinkoff.edu.java.scrapper.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatToLinkId implements Serializable {
    private String link;
    private Long chatId;
}
