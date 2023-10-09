package com.example.test_order_calculator_bot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BotUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userTlgId;
    private String firstName;
    private String lastName;
    private String username;
    private LocalDateTime dateOfFirstVisit;
    private LocalDateTime dateOfLastVisit;
    @OneToMany(mappedBy = "botUser", fetch = FetchType.EAGER)
    private List<Order> userOrders = new ArrayList<>();
}
