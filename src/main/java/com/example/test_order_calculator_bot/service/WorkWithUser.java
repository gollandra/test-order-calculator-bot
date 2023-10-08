package com.example.test_order_calculator_bot.service;

import com.example.test_order_calculator_bot.dao.BotUserDAO;
import com.example.test_order_calculator_bot.entity.BotUser;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;

@Service
public class WorkWithUser {

    private final BotUserDAO botUserDAO;

    public WorkWithUser(BotUserDAO botUserDAO) {
        this.botUserDAO = botUserDAO;
    }

    public void saveUser(Update update) {
        BotUser botUser = botUserDAO.findBotUserByChatId(update.getMessage().getChatId())
                .orElse(null);
        if (botUser == null) {
            botUser = new BotUser();
            botUser.setChatId(update.getMessage().getChatId());
            botUser.setFirstName(update.getMessage().getChat().getFirstName());
            botUser.setLastName(update.getMessage().getChat().getLastName());
            botUser.setUsername(update.getMessage().getChat().getUserName());
            botUser.setDateOfFirstVisit(LocalDateTime.now());
        }
        botUser.setDateOfLastVisit(LocalDateTime.now());
        botUserDAO.save(botUser);
    }
}
