package com.example.test_order_calculator_bot.bot.action;

import com.example.test_order_calculator_bot.bot.ProcessingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SumIncomingAction implements Action {

    @Override
    public BotApiMethod handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String text = "Введите сумму вхождения";
        ProcessingBot.bindingBy.put(chatId, text);

        return new SendMessage(chatId, text);
    }

    @Override
    public BotApiMethod callback(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String sumIncoming = update.getMessage().getText();

        ProcessingBot.orders.get(chatId).setSumIncoming(sumIncoming);
        ProcessingBot.bindingBy.remove(chatId);

        return new RiskAction().handle(update);
    }
}
