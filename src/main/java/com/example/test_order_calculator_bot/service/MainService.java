package com.example.test_order_calculator_bot.service;

import com.example.test_order_calculator_bot.action.*;
import com.example.test_order_calculator_bot.entity.Order;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Service
public class MainService {
    public static final Map<String, String> bindingBy = new HashMap<>();
    public static final Map<String, Order> orders = new HashMap<>();
    private final Map<String, Action> actions = new HashMap<>();

    public MainService(StartAction startAction,
                       SumIncomingAction sumIncomingAction,
                       RiskAction riskAction,
                       InfoAction infoAction,
                       CurrencyAction currencyAction,
                       BalanceAction balanceAction) {
        actions.put("/start", startAction);
        actions.put("Введите сумму вхождения", sumIncomingAction);
        actions.put("Введите процент риска", riskAction);
        actions.put("О боте", infoAction);
        actions.put("Введите текущий баланс", balanceAction);
        actions.put("Рассчитать стоимость", currencyAction);
    }

    public BotApiMethod<?> processingUpdate(Update update) {
        String key = update.getMessage().getText();
        String chatId = update.getMessage().getChatId().toString();
        BotApiMethod<?> message = null;
        if (actions.containsKey(key)) {
            message = actions.get(key).handle(update);
            bindingBy.put(chatId, key);
        } else if (bindingBy.containsKey(chatId)) {
            message = actions.get(bindingBy.get(chatId)).callback(update);
        }
        return message;
    }
}
