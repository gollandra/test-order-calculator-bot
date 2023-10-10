package com.example.test_order_calculator_bot.action;

import com.example.test_order_calculator_bot.dao.BotUserDAO;
import com.example.test_order_calculator_bot.entity.BotUser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class AdminUserAction implements Action {
    private final BotUserDAO botUserDAO;

    public AdminUserAction(BotUserDAO botUserDAO) {
        this.botUserDAO = botUserDAO;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        List<BotUser> users = botUserDAO.findLast10Users();
        StringBuilder text = new StringBuilder();
        users.forEach(u -> text.append(u.getFirstName()).append(" ")
                .append(u.getLastName())
                .append(":\n")
                .append("t.me/")
                .append(u.getUsername())
                .append("\n"));
        return new SendMessage(String.valueOf(update.getMessage().getChatId()), text.toString());
    }

    @Override
    public BotApiMethod<?> callback(Update update) {
        return handle(update);
    }
}
