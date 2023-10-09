package com.example.test_order_calculator_bot.action;

import com.example.test_order_calculator_bot.bot.KeyBoard;
import com.example.test_order_calculator_bot.entity.Order;
import com.example.test_order_calculator_bot.service.MainService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CurrencyAction implements Action {

    @Override
    public BotApiMethod<?> handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String text = "Выберите валюту в которой будет совершаться сделка";

        SendMessage sendMessage = new SendMessage(chatId, text);
        sendMessage.setReplyMarkup(KeyBoard.getKeyboardMarkupCurrency());

        MainService.orders.put(chatId, new Order());

        return sendMessage;
    }

    @Override
    public BotApiMethod<?> callback(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String currency = update.getMessage().getText();

        MainService.orders.get(chatId).setCurrency(currency);

        return new SumIncomingAction().handle(update);
    }
}
