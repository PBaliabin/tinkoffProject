package ru.tinkoff.edu.java.bot.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GithubLink {
    private String link;
    private String repositoryId;
    private String name;
    private String fullName;
    private LocalDateTime lastActivityTime;
    private Integer forksCount;
    private Integer openIssuesCount;
    private LocalDateTime lastCheckTime;
}
