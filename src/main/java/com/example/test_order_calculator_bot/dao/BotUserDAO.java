package com.example.test_order_calculator_bot.dao;

import com.example.test_order_calculator_bot.entity.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface BotUserDAO extends JpaRepository<BotUser, Long> {
    Optional<BotUser> findBotUserByUserTlgId(Long userTlgId);
}
