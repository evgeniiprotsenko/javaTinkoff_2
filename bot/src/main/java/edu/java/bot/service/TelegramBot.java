package edu.java.bot.service;

import edu.java.bot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;
    final CommandService commandService;

    public TelegramBot(BotConfig config, CommandService commandService) {
        this.config = config;
        this.commandService = commandService;
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    sendMessage(chatId, commandService.startCommand());
                    break;
                case "/help":
                    sendMessage(chatId, commandService.helpCommand());
                    break;
                case "/track":
                    sendMessage(chatId, commandService.trackCommand(""));
                    break;
                case "/untrack":
                    sendMessage(chatId, commandService.untrackCommand(""));
                    break;
                case "/list":
                    sendMessage(chatId, commandService.listCommand());
                    break;
                default:
                    sendMessage(chatId, commandService.unknownCommand());
                    break;
            }
        }
    }

    private void sendMessage(long chatId, String text) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
