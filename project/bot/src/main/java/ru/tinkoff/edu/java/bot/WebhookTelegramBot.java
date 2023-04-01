package ru.tinkoff.edu.java.bot;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.GetMyCommands;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tinkoff.edu.java.bot.configuration.TelegramBotConfig;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class WebhookTelegramBot extends TelegramWebhookBot {
    private final TelegramBotConfig telegramBotConfig;

    private static final String EMPTY_LINK_LIST_MESSAGE = "Список отслеживаемых ссылок пуст";

    public WebhookTelegramBot(TelegramBotConfig telegramBotConfig) {
        super(telegramBotConfig.getToken());
        this.telegramBotConfig = telegramBotConfig;
        List<BotCommand> botCommands = new ArrayList<>();
        botCommands.add(new BotCommand("/start", "зарегистрировать пользователя"));
        botCommands.add(new BotCommand("/help", "вывести окно с командами"));
        botCommands.add(new BotCommand("/track", "начать отслеживание ссылки"));
        botCommands.add(new BotCommand("/untrack", "прекратить отслеживание ссылки"));
        botCommands.add(new BotCommand("/list", "показать список отслеживаемых ссылок"));
        try {
            this.execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
            this.execute(SetWebhook.builder().url(telegramBotConfig.getWebhookPath()).build());
        } catch (TelegramApiException e) {
            System.out.println("Error setting bot's command list: " + e.getMessage());
        }
    }


    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start" -> {
                    this.buildAndSendTextMessage(chatId, "sorry, feature \"user registration\" is under construction");
                    System.out.println("attempt use unfinished command \"/start\"");
                }
                case "/help" -> {
                    String stringBuilder = "Ниже описаны все команды, поддерживаемые данным ботом:\n\n" +
                            this.getCommandListAsString();
                    this.buildAndSendTextMessage(chatId, stringBuilder);
                }
                case "/track" -> {
                    this.buildAndSendTextMessage(chatId, "sorry, feature \"track\" is under construction");
                    System.out.println("attempt use unfinished command \"/track\"");
                }
                case "/untrack" -> {
                    this.buildAndSendTextMessage(chatId, "sorry, feature \"untrack\" is under construction");
                    System.out.println("attempt use unfinished command \"/untrack\"");
                }
                case "/list" -> {
                    int random = (int) (Math.random() * 10);
                    random = random % 2;

                    if (random == 1) {
                        this.buildAndSendTextMessage(chatId, EMPTY_LINK_LIST_MESSAGE);
                    } else {
                        this.buildAndSendTextMessage(chatId, "url1\nurl2");
                    }
                }
                default -> this.buildAndSendTextMessage(chatId, "Извините, данная команда не поддерживается");
            }
        }
        return null;
    }

    private List<BotCommand> getCommandsList() {
        List<BotCommand> botCommandList;
        try {
            botCommandList = this.execute(new GetMyCommands());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        return botCommandList;
    }

    private String getCommandListAsString() {
        List<BotCommand> botCommandList = this.getCommandsList();
        StringBuilder stringBuilder = new StringBuilder();
        botCommandList.forEach(botCommand -> stringBuilder
                .append("/")
                .append(botCommand.getCommand())
                .append(" - ")
                .append(botCommand.getDescription())
                .append("\n"));
        return stringBuilder.toString();
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
