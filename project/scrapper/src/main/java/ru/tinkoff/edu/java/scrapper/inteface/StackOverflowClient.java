package ru.tinkoff.edu.java.scrapper.inteface;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import ru.tinkoff.edu.java.scrapper.dto.StackoverflowResponse;

public interface StackOverflowClient {
    @GetExchange("/questions/{questionNumber}")
    StackoverflowResponse getQuestion(@PathVariable String questionNumber, @RequestParam(value = "site", defaultValue = "stackoverflow") String site);
}
