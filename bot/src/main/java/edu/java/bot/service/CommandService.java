package edu.java.bot.service;

import org.springframework.stereotype.Service;

@Service
public class CommandService {

    public String startCommand() {
        return "Добро пожаловать! Для начала работы используйте команду /help";
    }

    public String helpCommand() {
        return "Список команд:\n"
            + "/start - зарегистрировать пользователя\n"
            + "/help - вывести окно с командами\n"
            + "/track - начать отслеживание ссылки\n"
            + "/untrack - прекратить отслеживание ссылки\n"
            + "/list - показать список отслеживаемых ссылок";
    }

    public String trackCommand(String link) {
        // Здесь будет логика обработки команды /track
        return "Вы начали отслеживание ссылки: " + link;
    }

    public String untrackCommand(String link) {
        // Здесь будет логика обработки команды /untrack
        return "Вы прекратили отслеживание ссылки: " + link;
    }

    public String listCommand() {
        // Здесь будет логика обработки команды /list
        return "Список отслеживаемых ссылок:\n"
            + "https://github.com/example1\n"
            + "https://stackoverflow.com/example2";
    }
}
