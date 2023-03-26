package ru.tinkoff.edu.java.scrapper.dto;

import lombok.Data;

@Data
public class StackoverflowResponse {

    private Integer quota_max;
    private Integer quota_remaining;
}
