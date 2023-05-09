package ru.tinkoff.edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.scrapper.dto.AccessType;
import ru.tinkoff.edu.java.scrapper.dto.RabbitConfigurationParameters;
import ru.tinkoff.edu.java.scrapper.dto.Scheduler;
import ru.tinkoff.edu.java.scrapper.inteface.client.TgBotClient;
import ru.tinkoff.edu.java.scrapper.service.TgBotClientService;


@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test,
                                @NotNull Scheduler scheduler,
                                @NotNull AccessType databaseAccessType,
                                @NotNull RabbitConfigurationParameters rabbitConfigurationParameters,
                                @NotNull Boolean useQueue) {

    @Bean
    public Long schedulerIntervalMs() {
        return scheduler.interval().toMillis();
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

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
    public TgBotClientService tgBotClientService(TgBotClient tgBotClient) {
        return new TgBotClientService(tgBotClient);
    }
}
