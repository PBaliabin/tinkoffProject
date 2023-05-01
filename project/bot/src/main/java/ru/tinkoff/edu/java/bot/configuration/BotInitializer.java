package ru.tinkoff.edu.java.bot.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.tinkoff.edu.java.bot.WebhookTelegramBot;

@Component
@RequiredArgsConstructor
public class BotInitializer {
    private final WebhookTelegramBot webhookTelegramBot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(webhookTelegramBot, SetWebhook.builder().url(webhookTelegramBot.getTelegramBotConfig().webhookPath()).build());
            System.out.println("Running webhookTelegramBot");
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }
}