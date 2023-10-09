package com.example.test_order_calculator_bot.action;

import com.example.test_order_calculator_bot.bot.KeyBoard;
import com.example.test_order_calculator_bot.entity.Order;
import com.example.test_order_calculator_bot.service.MainService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class BalanceAction implements Action {
    @Override
    public BotApiMethod<?> handle(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String text = "Введите текущий баланс";

        MainService.bindingBy.put(chatId, text);

        return new SendMessage(chatId, text);
    }

    @Override
    public BotApiMethod<?> callback(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String balance = update.getMessage().getText();

        MainService.orders.get(chatId).setBalance(balance);
        MainService.bindingBy.remove(chatId);
        Order order = MainService.orders.get(chatId);

        String text = order.computeOrder() == null
                ? "Что то пошло не так"
                : String.format("""
                Ваши данные:
                Валюта рассчета - %s
                Сумма вхождения - %s
                Процент риска - %s
                Текущий баланс - %s
                                
                Ваша ставка - %f
                """, order.getCurrency(), order.getSumIncoming(), order.getRisk(), order.getBalance(), order.computeOrder());

        MainService.orders.remove(chatId);

        SendMessage message = new SendMessage(chatId, text);
        message.setReplyMarkup(KeyBoard.getReplyKeyboardMarkup());
        return message;
    }
}
