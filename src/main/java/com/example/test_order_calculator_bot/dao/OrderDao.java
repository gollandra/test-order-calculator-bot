package com.example.test_order_calculator_bot.dao;

import com.example.test_order_calculator_bot.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM bot_user_order ORDER BY date_time DESC LIMIT 5", nativeQuery = true)
    List<Order> findFirst5Orders();
}
