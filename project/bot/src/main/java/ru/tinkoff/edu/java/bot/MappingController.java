package ru.tinkoff.edu.java.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequiredArgsConstructor
public class MappingController {
    private final LongPollingTelegramBot longPollingTelegramBot;
    private final WebhookTelegramBot webhookTelegramBot;

    @PostMapping("/")
    public void onUpdate(@RequestBody Update update) {
        longPollingTelegramBot.onUpdateReceived(update);
        webhookTelegramBot.onWebhookUpdateReceived(update);
    }
}
