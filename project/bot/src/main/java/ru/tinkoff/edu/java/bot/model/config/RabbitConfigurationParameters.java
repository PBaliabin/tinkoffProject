package ru.tinkoff.edu.java.bot.model.config;


public record RabbitConfigurationParameters(
        String queueName,
        String exchangeName,
        String routingKey) {
}
