package ru.tinkoff.edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

@Data
public class StackoverflowResponse {
    @JsonProperty("quota_max")
    private Integer quotaMax;
    @JsonProperty("quota_remaining")
    private Integer quotaRemaining;

    private String lastActivityDate;
    private Boolean isAnswered;
    private Integer answerCount;

    @JsonProperty("items")
    private void unpackItems(ArrayList<Map<String, Object>> items) {
        this.setLastActivityDate(((Integer) items.get(0).get("last_activity_date")).toString());
        this.setIsAnswered((Boolean) items.get(0).get("is_answered"));
        this.setAnswerCount((Integer) items.get(0).get("answer_count"));
    }
}
