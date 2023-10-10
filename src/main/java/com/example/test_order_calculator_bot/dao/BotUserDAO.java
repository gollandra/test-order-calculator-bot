package com.example.test_order_calculator_bot.dao;

import com.example.test_order_calculator_bot.entity.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BotUserDAO extends JpaRepository<BotUser, Long> {
    Optional<BotUser> findBotUserByUserTlgId(Long userTlgId);

    @Query(value = "SELECT * FROM bot_user ORDER BY date_of_last_visit desc LIMIT 10", nativeQuery = true)
    List<BotUser> findLast10Users();
}
