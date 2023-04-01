package ru.tinkoff.edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.bot.LongPollingTelegramBot;
import ru.tinkoff.edu.java.bot.WebhookTelegramBot;

@Validated
@PropertySources({
        @PropertySource("classpath:application.yaml"),
        @PropertySource("classpath:application.properties")
})
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test, @NotNull TelegramBotConfig telegramBotConfig) {

    @Bean
    public LongPollingTelegramBot telegramBot() {
        return new LongPollingTelegramBot(telegramBotConfig);
    }

    @Bean
    public WebhookTelegramBot webhookTelegramBot() {
        return new WebhookTelegramBot(telegramBotConfig);
    }
}