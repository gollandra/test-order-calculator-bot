package com.example.test_order_calculator_bot.service;

import com.example.test_order_calculator_bot.dao.BotUserDAO;
import com.example.test_order_calculator_bot.dao.OrderDao;
import com.example.test_order_calculator_bot.entity.BotUser;
import com.example.test_order_calculator_bot.entity.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final BotUserDAO botUserDAO;
    private final OrderDao orderDao;

    public UserService(BotUserDAO botUserDAO, OrderDao orderDao) {
        this.botUserDAO = botUserDAO;
        this.orderDao = orderDao;
    }

    public void saveUser(BotUser botUser, Order order) {
        BotUser botUserFromDb = botUserDAO.findBotUserByUserTlgId(botUser.getUserTlgId())
                .orElse(null);
        if (botUserFromDb == null) {
            botUserDAO.save(botUser);
        } else {
            botUserFromDb.setDateOfLastVisit(botUser.getDateOfLastVisit());
            if (order != null && order.getBalance() != null) {
                order.setDateTime(LocalDateTime.now());
                order.setBotUser(botUserFromDb);
                botUserFromDb.getUserOrders().add(order);
                orderDao.save(order);
            }
            botUserDAO.save(botUserFromDb);
        }
    }
}
