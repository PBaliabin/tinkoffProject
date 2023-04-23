package ru.tinkoff.edu.java.bot;

import lombok.Getter;
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
import ru.tinkoff.edu.java.bot.configuration.TelegramBotConfig;
import ru.tinkoff.edu.java.bot.model.dto.LinkChangeLog;
import ru.tinkoff.edu.java.bot.service.TelegramBotService;
import ru.tinkoff.edu.java.bot.service.wrapper.GithubLinkWrapper;
import ru.tinkoff.edu.java.bot.service.wrapper.StackoverflowLinkWrapper;

import java.util.List;

@Getter
@RestController
public class WebhookTelegramBot extends TelegramWebhookBot {
    private final TelegramBotConfig telegramBotConfig;
    private final TelegramBotService telegramBotService;

    public WebhookTelegramBot(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") TelegramBotConfig telegramBotConfig,
                              TelegramBotService telegramBotService,
                              List<BotCommand> botCommands) {
        super(telegramBotConfig.getToken());
        this.telegramBotConfig = telegramBotConfig;

        try {
            this.execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
            this.execute(SetWebhook.builder().url(telegramBotConfig.getWebhookPath()).build());
        } catch (TelegramApiException e) {
            System.out.println("Error setting bot's command list: " + e.getMessage());
        }

        this.telegramBotService = telegramBotService;
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
    public void processUpdate(@RequestBody List<LinkChangeLog> linkChangeLogList) {
        for (LinkChangeLog changelog : linkChangeLogList) {
            String stringBuilder = "Telegram chat " + changelog.getTgChatId() + " has next updates:\n" +
                    GithubLinkWrapper.makeMessage(
                            changelog.getOutdatedGithubLinks(),
                            changelog.getUpdatedGithubLinks()
                    ) +
                    StackoverflowLinkWrapper.makeMessage(
                            changelog.getOutdatedStackoverflowLinks(),
                            changelog.getUpdatedStackoverflowLinks()
                    );
            System.out.println(stringBuilder);
        }
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
        return telegramBotConfig.getWebhookPath();
    }

    @Override
    public String getBotUsername() {
        return telegramBotConfig.getBotName();
    }
}
