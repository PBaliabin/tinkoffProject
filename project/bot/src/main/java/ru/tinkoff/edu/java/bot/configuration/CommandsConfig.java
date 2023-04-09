package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

@Configuration
public class CommandsConfig {

    @Bean
    public BotCommand startBotCommand() {
        return new BotCommand("/start", "зарегистрировать пользователя");
    }

    @Bean
    public BotCommand helpBotCommand() {
        return new BotCommand("/help", "вывести окно с командами");
    }

    @Bean
    public BotCommand trackBotCommand() {
        return new BotCommand("/track", "начать отслеживание ссылки");
    }

    @Bean
    public BotCommand untrackBotCommand() {
        return new BotCommand("/untrack", "прекратить отслеживание ссылки");
    }

    @Bean
    public BotCommand listBotCommand() {
        return new BotCommand("/list", "показать список отслеживаемых ссылок");
    }
}
