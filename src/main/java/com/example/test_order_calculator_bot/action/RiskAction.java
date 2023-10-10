package com.example.test_order_calculator_bot.action;

import com.example.test_order_calculator_bot.service.MainService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class RiskAction implements Action {
    @Override
    public BotApiMethod<?> handle(Update update) {
        MainService.bindingBy.put(update.getMessage().getChatId().toString(), NameOfActions.RISK_ACTION);

        return new SendMessage(update.getMessage().getChatId().toString(), NameOfActions.RISK_ACTION);
    }

    @Override
    public BotApiMethod<?> callback(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String currency = update.getMessage().getText();

        MainService.orders.get(chatId).setRisk(currency);

        return new BalanceAction().handle(update);
    }
}
