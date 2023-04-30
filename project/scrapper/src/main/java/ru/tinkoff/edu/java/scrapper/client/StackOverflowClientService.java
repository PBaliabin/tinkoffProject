package ru.tinkoff.edu.java.scrapper.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.response.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.inteface.client.StackOverflowClient;

@Service
@RequiredArgsConstructor
public class StackOverflowClientService {
    private final StackOverflowClient webClient;

    public StackoverflowResponse getQuestion(String questionNumber, String site) {
        return webClient.getQuestion(questionNumber, site);
    }
}
