package com.example.test_order_calculator_bot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BotUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String firstName;
    private String lastName;
    private String username;
    private LocalDateTime dateOfFirstVisit;
    private LocalDateTime dateOfLastVisit;
    @OneToMany(mappedBy = "botUser")
    private Set<Order> userOrders;
}
