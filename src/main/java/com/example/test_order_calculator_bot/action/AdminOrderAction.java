package com.example.test_order_calculator_bot.action;

import com.example.test_order_calculator_bot.dao.OrderDao;
import com.example.test_order_calculator_bot.entity.BotUser;
import com.example.test_order_calculator_bot.entity.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class AdminOrderAction implements Action {

    private final OrderDao orderDao;

    public AdminOrderAction(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        List<Order> orders = orderDao.findFirst5Orders();
        StringBuilder text = new StringBuilder();
        orders.forEach(o -> {
            BotUser user = o.getBotUser();
            text.append(user.getFirstName())
                    .append(" ")
                    .append(user.getLastName())
                    .append(":\n")
                    .append(String.format("""
                            Входные данные:
                            Валюта рассчета - %s
                            Сумма вхождения - %s
                            Процент риска - %s
                            Текущий баланс - %s
                            Результат - %f
                                            
                            """, o.getCurrency(), o.getSumIncoming(), o.getRisk(), o.getBalance(), o.computeOrder()));
        });
        return new SendMessage(String.valueOf(update.getMessage().getChatId()), text.toString());
    }

    @Override
    public BotApiMethod<?> callback(Update update) {
        return handle(update);
    }
}
