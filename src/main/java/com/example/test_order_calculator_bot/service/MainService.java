package com.example.test_order_calculator_bot.service;

import com.example.test_order_calculator_bot.action.*;
import com.example.test_order_calculator_bot.entity.BotUser;
import com.example.test_order_calculator_bot.entity.Order;
import com.example.test_order_calculator_bot.utils.UserUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainService {
    public static final Map<String, String> bindingBy = new HashMap<>();
    public static final Map<String, Order> orders = new HashMap<>();
    private final Map<String, Action> actions = new HashMap<>();
    private final UserService userService;
    @Value("#{'${bot.admin}'.split(',')}")
    private List<String> listOfAdmins;

    public MainService(StartAction startAction,
                       SumIncomingAction sumIncomingAction,
                       RiskAction riskAction,
                       InfoAction infoAction,
                       CurrencyAction currencyAction,
                       BalanceAction balanceAction, UserService userService) {
        this.userService = userService;
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

        BotUser botUser = UserUtils.getBotUserFromUpdate(update);
        Order order = null;

        if (actions.containsKey(key)) {
            message = actions.get(key).handle(update);
            bindingBy.put(chatId, key);
        } else if (bindingBy.containsKey(chatId)) {
            message = actions.get(bindingBy.get(chatId)).callback(update);
            if (bindingBy.get(chatId).equals("Введите текущий баланс")) {
                order = orders.get(chatId);
            }
        }

        userService.saveUser(botUser, order);

        return message;
    }
}
