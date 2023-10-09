package com.example.test_order_calculator_bot.bot;

import com.example.test_order_calculator_bot.service.MainService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class ProcessingBot extends TelegramLongPollingBot {

    private final MainService mainService;
    @Value("${bot.name}")
    String botUsername;

    public ProcessingBot(@Value("${bot.token}") String botToken, MainService mainService) {
        super(botToken);
        this.mainService = mainService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        BotApiMethod<?> message = mainService.processingUpdate(update);
        sendMessage(message);
    }

    private void sendMessage(BotApiMethod<?> message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
