package ru.tinkoff.edu.java.bot.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkChangeLog {
    private Link oldVersion;
    private Link newVersion;
}
