package com.example.test_order_calculator_bot.dao;

import com.example.test_order_calculator_bot.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
}
