package ru.tinkoff.edu.java.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.GetMyCommands;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tinkoff.edu.java.bot.configuration.TelegramBotConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LongPollingTelegramBot extends TelegramLongPollingBot {
    private final TelegramBotConfig telegramBotConfig;

    public LongPollingTelegramBot(TelegramBotConfig telegramBotConfig) {
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
        } catch (TelegramApiException e) {
            System.out.println("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return telegramBotConfig.getBotName();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start" -> {
                    try {
                        this.execute(new SendMessage(String.valueOf(chatId), "hi"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "/help" -> {
                    try {
                        this.execute(new SendMessage(String.valueOf(chatId), "HELP_TEXT"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "/track" -> {
                    try {
                        this.execute(new SendMessage(String.valueOf(chatId), "Track"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "/untrack" -> {
                    try {
                        this.execute(new SendMessage(String.valueOf(chatId), "Untrack"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "/list" -> {
                    try {
                        String listOfCommands = Arrays.toString(this.execute(new GetMyCommands()).toArray());
                        this.execute(new SendMessage(String.valueOf(chatId), listOfCommands));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
                default -> {
                    try {
                        this.execute(new SendMessage(String.valueOf(chatId), "Sorry, command was not recognized"));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
