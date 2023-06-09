package ru.tinkoff.edu.java.bot.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.tinkoff.edu.java.bot.WebhookTelegramBot;

@Slf4j
@Component
@RequiredArgsConstructor
public class BotInitializer {
    private final WebhookTelegramBot webhookTelegramBot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(webhookTelegramBot,
                                        SetWebhook.builder()
                                                  .url(webhookTelegramBot.getTelegramBotConfig().webhookPath())
                                                  .build());
            log.info("Running webhookTelegramBot");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
