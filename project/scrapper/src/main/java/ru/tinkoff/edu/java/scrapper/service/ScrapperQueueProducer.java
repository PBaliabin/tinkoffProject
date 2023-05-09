package ru.tinkoff.edu.java.scrapper.service;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdate;
import ru.tinkoff.edu.java.scrapper.inteface.service.MessageService;

@Service
@AllArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class ScrapperQueueProducer implements MessageService {

    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;
    private String routingKey;

    @Override
    public void send(LinkUpdate update) {
        rabbitTemplate.convertAndSend(directExchange.getName(), routingKey, update);
    }
}
