package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.StackoverflowResponse;
import ru.tinkoff.edu.java.scrapper.inteface.StackOverflowClient;

@Service
public class StackOverflowClientService {
    private final StackOverflowClient webClient;

    public StackOverflowClientService(@Qualifier("stackOverflowWebClient") StackOverflowClient webClient) {
        this.webClient = webClient;
    }

    public StackoverflowResponse getQuestion(String questionNumber, String site) {
        return webClient.getQuestion(questionNumber, site);
    }

}
