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
                       BalanceAction balanceAction,
                       AdminUserAction adminUserAction,
                       AdminOrderAction adminOrderAction,
                       UserService userService) {
        this.userService = userService;
        actions.put(NameOfActions.START_ACTION, startAction);
        actions.put(NameOfActions.SUM_INCOMING_ACTION, sumIncomingAction);
        actions.put(NameOfActions.RISK_ACTION, riskAction);
        actions.put(NameOfActions.INFO_ACTION, infoAction);
        actions.put(NameOfActions.BALANCE_ACTION, balanceAction);
        actions.put(NameOfActions.CURRENCY_ACTION, currencyAction);
        actions.put(NameOfActions.ADMIN_USER_ACTION, adminUserAction);
        actions.put(NameOfActions.ADMIN_ORDER_ACTION, adminOrderAction);
    }

    public BotApiMethod<?> processingUpdate(Update update) {
        String key = update.getMessage().getText();
        String chatId = update.getMessage().getChatId().toString();
        BotApiMethod<?> message = null;

        BotUser botUser = UserUtils.getBotUserFromUpdate(update);
        Order order = null;

        if (actions.containsKey(key)) {
            if (!key.equals(NameOfActions.ADMIN_USER_ACTION) && !key.equals(NameOfActions.ADMIN_ORDER_ACTION)) {
                message = actions.get(key).handle(update);
                bindingBy.put(chatId, key);
            } else if (listOfAdmins.contains(update.getMessage().getFrom().getId().toString())) {
                message = actions.get(key).handle(update);
            }
        } else if (bindingBy.containsKey(chatId)) {
            message = actions.get(bindingBy.get(chatId)).callback(update);
            if (bindingBy.get(chatId).equals(NameOfActions.BALANCE_ACTION)) {
                order = orders.get(chatId);
            }
        }

        userService.saveUser(botUser, order);

        return message;
    }
}
