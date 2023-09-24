package com.example.test_order_calculator_bot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ProcessingBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {

    }

    public ProcessingBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public String getBotUsername() {
        return "testOrderCalculatorBot";
    }
}
