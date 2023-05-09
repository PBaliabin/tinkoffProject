package ru.tinkoff.edu.java.bot.model.config;


public record TelegramBotConfig(String apiUrl,
                                String webhookPath,
                                String token,
                                String botName) {
}
