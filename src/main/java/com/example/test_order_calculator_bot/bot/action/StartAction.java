package com.example.test_order_calculator_bot.bot.action;

import com.example.test_order_calculator_bot.bot.KeyBoard;
import com.example.test_order_calculator_bot.bot.ProcessingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartAction implements Action {
    @Override
    public BotApiMethod handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        String text = "Hi, " +
                update.getMessage().getChat().getFirstName();

        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), text);
        sendMessage.setReplyMarkup(KeyBoard.getReplyKeyboardMarkup());

        return sendMessage;
    }

    @Override
    public BotApiMethod callback(Update update) {
        ProcessingBot.bindingBy.remove(update.getMessage().getChatId().toString());
        return handle(update);
    }
}
