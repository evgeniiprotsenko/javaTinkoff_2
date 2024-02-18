package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.CommandService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

    @Bean
    public TelegramBot bot(ApplicationConfig applicationConfig, CommandService commandService) {
        return new TelegramBot(applicationConfig.telegramToken());
    }

    @Component
    public static class MyBot {

        private final TelegramBot bot;
        private final CommandService commandService;

        public MyBot(TelegramBot bot, CommandService commandService) {
            this.bot = bot;
            this.commandService = commandService;
        }

        public void onUpdateReceived(Update update) {
            if (update.message() != null && update.message().text() != null) {
                String messageText = update.message().text();
                String responseText;

                switch (messageText) {
                    case "/start":
                        responseText = commandService.startCommand();
                        break;
                    case "/help":
                        responseText = commandService.helpCommand();
                        break;
                    case "/track":
                        responseText = commandService.trackCommand("");
                        break;
                    case "/untrack":
                        responseText = commandService.untrackCommand("");
                        break;
                    case "/list":
                        responseText = commandService.listCommand();
                        break;
                    default:
                        responseText = "Команда неизвестна";
                        break;
                }

                bot.execute(new SendMessage(update.message().chat().id(), responseText));
            }
        }
    }
}
