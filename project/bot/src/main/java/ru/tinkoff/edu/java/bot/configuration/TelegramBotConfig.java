package ru.tinkoff.edu.java.bot.configuration;

import lombok.Data;

@Data
public class TelegramBotConfig {
    String apiUrl;
    String webhookPath;
    String token;
    String botName;
}
