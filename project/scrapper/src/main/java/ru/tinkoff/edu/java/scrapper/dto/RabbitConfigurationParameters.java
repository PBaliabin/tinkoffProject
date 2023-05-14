package ru.tinkoff.edu.java.scrapper.dto;


public record RabbitConfigurationParameters(
        String queueName,
        String exchangeName,
        String routingKey) {
}
