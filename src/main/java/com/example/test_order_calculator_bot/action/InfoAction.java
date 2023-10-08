package com.example.test_order_calculator_bot.action;

import com.example.test_order_calculator_bot.bot.KeyBoard;
import com.example.test_order_calculator_bot.bot.ProcessingBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class InfoAction implements Action{
    @Override
    public BotApiMethod handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        String text = "Какая то полезная информация о боте";

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
