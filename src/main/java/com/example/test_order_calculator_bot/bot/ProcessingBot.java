package com.example.test_order_calculator_bot.bot;

import com.example.test_order_calculator_bot.action.*;
import com.example.test_order_calculator_bot.entity.Order;
import com.example.test_order_calculator_bot.service.WorkWithUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProcessingBot extends TelegramLongPollingBot {

    public static final Map<String, String> bindingBy = new HashMap<>();
    public static final Map<String, Order> orders = new HashMap<>();
    private final Map<String, Action> actions = new HashMap<>() {{
        put("О боте", new InfoAction());
        put("/start", new StartAction());
        put("Рассчитать стоимость", new CurrencyAction());
        put("Введите сумму вхождения", new SumIncomingAction());
        put("Введите процент риска", new RiskAction());
        put("Введите текущий баланс", new BalanceAction());
    }};
    @Value("${bot.name}")
    String botUsername;

    private final WorkWithUser workWithUser;

    public ProcessingBot(@Value("${bot.token}") String botToken, WorkWithUser workWithUser) {
        super(botToken);
        this.workWithUser = workWithUser;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String key = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            if (actions.containsKey(key)) {
                BotApiMethod message = actions.get(key).handle(update);
                bindingBy.put(chatId, key);
                sendMessage(message);
            } else if (bindingBy.containsKey(chatId)) {
                BotApiMethod message = actions.get(bindingBy.get(chatId)).callback(update);
                sendMessage(message);
            }
            workWithUser.saveUser(update);
        }
    }

    private void sendMessage(BotApiMethod message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
