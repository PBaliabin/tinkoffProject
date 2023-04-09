package ru.tinkoff.edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.validation.annotation.Validated;

@Validated
@PropertySources({
        @PropertySource("classpath:application.yaml"),
        @PropertySource("classpath:application.properties")
})
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test, @NotNull TelegramBotConfig telegramBotConfig) {

    @Bean
    public TelegramBotConfig telegramBotConfig() {
        return this.telegramBotConfig;
    }
}