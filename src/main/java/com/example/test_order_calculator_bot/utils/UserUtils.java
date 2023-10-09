package com.example.test_order_calculator_bot.utils;

import com.example.test_order_calculator_bot.entity.BotUser;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;

public final class UserUtils {
    public static BotUser getBotUserFromUpdate(Update update) {
        BotUser botUser = new BotUser();
        botUser.setUserTlgId(update.getMessage().getFrom().getId());
        botUser.setFirstName(update.getMessage().getChat().getFirstName());
        botUser.setLastName(update.getMessage().getChat().getLastName());
        botUser.setUsername(update.getMessage().getChat().getUserName());
        botUser.setDateOfFirstVisit(LocalDateTime.now());
        botUser.setDateOfLastVisit(LocalDateTime.now());
        return botUser;
    }
}
