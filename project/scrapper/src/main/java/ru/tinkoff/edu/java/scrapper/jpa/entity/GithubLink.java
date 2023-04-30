package ru.tinkoff.edu.java.scrapper.jpa.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class GithubLink {
    @Id
    private String link;
    private String repositoryId;
    private String name;
    private String fullName;
    private LocalDateTime lastActivityTime;
    private Integer forksCount;
    private Integer openIssuesCount;
    private LocalDateTime lastCheckTime;
}
