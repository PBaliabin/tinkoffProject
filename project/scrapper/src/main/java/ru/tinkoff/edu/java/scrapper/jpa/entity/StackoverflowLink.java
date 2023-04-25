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
public class StackoverflowLink {
    @Id
    private String link;
    private Integer quotaMax;
    private Integer quotaRemaining;
    private LocalDateTime lastActivityTime;
    private Boolean isAnswered;
    private Integer answerCount;
    private LocalDateTime lastCheckTime;
}
