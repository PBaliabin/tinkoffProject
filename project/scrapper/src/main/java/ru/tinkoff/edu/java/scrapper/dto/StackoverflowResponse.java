package ru.tinkoff.edu.java.scrapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StackoverflowResponse {
    @JsonProperty("quota_max")
    private Integer quotaMax;
    @JsonProperty("quota_remaining")
    private Integer quotaRemaining;
    @JsonProperty("items[0].last_activity_date")
    private Long lastActivityDate;
}
