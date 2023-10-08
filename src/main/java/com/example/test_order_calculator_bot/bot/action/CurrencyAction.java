package com.example.test_order_calculator_bot.bot.action;

import com.example.test_order_calculator_bot.bot.KeyBoard;
import com.example.test_order_calculator_bot.bot.ProcessingBot;
import com.example.test_order_calculator_bot.entity.Order;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CurrencyAction implements Action {

    @Override
    public BotApiMethod handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String text = "Выберите валюту в которой будет совершаться сделка";

        SendMessage sendMessage = new SendMessage(chatId, text);
        sendMessage.setReplyMarkup(KeyBoard.getKeyboardMarkupCurrency());

        ProcessingBot.orders.put(chatId, new Order());

        return sendMessage;
    }

    @Override
    public BotApiMethod callback(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String currency = update.getMessage().getText();

        ProcessingBot.orders.get(chatId).setCurrency(currency);
        ProcessingBot.bindingBy.remove(chatId);

        return new SumIncomingAction().handle(update);
    }
}
