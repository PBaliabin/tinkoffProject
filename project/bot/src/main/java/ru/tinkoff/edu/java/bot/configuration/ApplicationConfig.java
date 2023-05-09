package ru.tinkoff.edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.bot.model.config.RabbitConfigurationParameters;
import ru.tinkoff.edu.java.bot.model.config.TelegramBotConfig;

@Validated
@PropertySources({
        @PropertySource("classpath:application.yaml"),
        @PropertySource("classpath:application.properties")
})
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test,
                                @NotNull TelegramBotConfig telegramBotConfig,
                                @NotNull RabbitConfigurationParameters rabbitConfigurationParameters) {

    @Bean
    public TelegramBotConfig telegramBotConfig() {
        return this.telegramBotConfig;
    }


    @Bean
    public String queueName() {
        return rabbitConfigurationParameters.queueName();
    }

    @Bean
    public String exchangeName() {
        return rabbitConfigurationParameters.exchangeName();
    }

    @Bean
    public String routingKey() {
        return rabbitConfigurationParameters.routingKey();
    }

}
