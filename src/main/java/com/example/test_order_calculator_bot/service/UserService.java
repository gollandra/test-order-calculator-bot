package com.example.test_order_calculator_bot.service;

import com.example.test_order_calculator_bot.dao.BotUserDAO;
import com.example.test_order_calculator_bot.entity.BotUser;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final BotUserDAO botUserDAO;

    public UserService(BotUserDAO botUserDAO) {
        this.botUserDAO = botUserDAO;
    }

    public void saveUser(Update update) {
        BotUser botUser = botUserDAO.findBotUserByUserTlgId(update.getMessage().getFrom().getId())
                .orElse(null);
        if (botUser == null) {
            botUser = new BotUser();
            botUser.setUserTlgId(update.getMessage().getFrom().getId());
            botUser.setFirstName(update.getMessage().getChat().getFirstName());
            botUser.setLastName(update.getMessage().getChat().getLastName());
            botUser.setUsername(update.getMessage().getChat().getUserName());
            botUser.setDateOfFirstVisit(LocalDateTime.now());
        }
        botUser.setDateOfLastVisit(LocalDateTime.now());
        botUserDAO.save(botUser);
    }
}
