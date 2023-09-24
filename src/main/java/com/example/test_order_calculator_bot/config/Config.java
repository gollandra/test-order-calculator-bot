package com.example.test_order_calculator_bot.config;

import com.example.test_order_calculator_bot.bot.ProcessingBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class Config {

    @Bean
    public TelegramBotsApi telegramBotsApi(ProcessingBot processingBot) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi((DefaultBotSession.class));
        telegramBotsApi.registerBot(processingBot);
        return telegramBotsApi;
    }
}
