package com.example.test_order_calculator_bot.action;

import com.example.test_order_calculator_bot.service.MainService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SumIncomingAction implements Action {

    @Override
    public BotApiMethod<?> handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String text = "Введите сумму вхождения";
        MainService.bindingBy.put(chatId, text);

        return new SendMessage(chatId, text);
    }

    @Override
    public BotApiMethod<?> callback(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String sumIncoming = update.getMessage().getText();

        MainService.orders.get(chatId).setSumIncoming(sumIncoming);

        return new RiskAction().handle(update);
    }
}
