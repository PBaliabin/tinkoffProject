package ru.tinkoff.edu.java.bot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    private String link;
    private Timestamp lastActivityTime;
    private Timestamp lastCheckTime;
}
