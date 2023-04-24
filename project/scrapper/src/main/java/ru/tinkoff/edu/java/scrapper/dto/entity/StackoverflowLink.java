package ru.tinkoff.edu.java.scrapper.dto.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StackoverflowLink {
    private String link;
    private Integer quotaMax;
    private Integer quotaRemaining;
    private LocalDateTime lastActivityTime;
    private Boolean isAnswered;
    private Integer answerCount;
    private LocalDateTime lastCheckTime;
}
