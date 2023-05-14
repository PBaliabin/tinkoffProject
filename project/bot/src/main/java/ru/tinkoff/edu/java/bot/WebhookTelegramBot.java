package ru.tinkoff.edu.java.bot;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tinkoff.edu.java.bot.model.config.TelegramBotConfig;
import ru.tinkoff.edu.java.bot.model.dto.LinkUpdate;
import ru.tinkoff.edu.java.bot.service.TelegramBotService;
import ru.tinkoff.edu.java.bot.service.UpdateProcessService;

import java.util.List;
import java.util.Map;

@Getter
@Slf4j
@RestController
public class WebhookTelegramBot extends TelegramWebhookBot {
    private final TelegramBotConfig telegramBotConfig;
    private final TelegramBotService telegramBotService;
    private final UpdateProcessService updateProcessService;

    public WebhookTelegramBot(
            @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") TelegramBotConfig telegramBotConfig,
            TelegramBotService telegramBotService,
            List<BotCommand> botCommands,
            UpdateProcessService updateProcessService) {
        super(telegramBotConfig.token());
        this.telegramBotConfig = telegramBotConfig;

        try {
            this.execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
            this.execute(SetWebhook.builder().url(telegramBotConfig.webhookPath()).build());
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: "
                              + e.getMessage());
        }

        this.telegramBotService = telegramBotService;
        this.updateProcessService = updateProcessService;
    }

    @Override
    @PostMapping("/")
    public BotApiMethod<?> onWebhookUpdateReceived(@RequestBody Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            this.buildAndSendTextMessage(chatId, telegramBotService.processMessage(messageText));
        }
        return null;
    }

    @PostMapping("/update")
    public void processUpdate(@RequestBody LinkUpdate linkUpdate) {
        Map<String, String> processedUpdate = updateProcessService.processUpdate(linkUpdate);
        String logMessage = "\nTelegram chat with id="
                + processedUpdate.get(UpdateProcessService.TG_CHAT_ID)
                + " has following updates:\n"
                + processedUpdate.get(UpdateProcessService.MESSAGE);
        log.info(logMessage);
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void buildAndSendTextMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        this.sendMessage(sendMessage);
    }

    @Override
    public String getBotPath() {
        return telegramBotConfig.webhookPath();
    }

    @Override
    public String getBotUsername() {
        return telegramBotConfig.webhookPath();
    }
}
