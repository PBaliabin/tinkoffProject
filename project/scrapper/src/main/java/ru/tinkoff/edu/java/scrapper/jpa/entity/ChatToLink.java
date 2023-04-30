package ru.tinkoff.edu.java.scrapper.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@IdClass(ChatToLinkId.class)
@NoArgsConstructor
@AllArgsConstructor
public class ChatToLink {
    @Id
    private String link;
    @Id
    private Long chatId;
}
