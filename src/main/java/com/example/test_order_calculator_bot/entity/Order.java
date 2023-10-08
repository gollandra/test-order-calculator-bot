package com.example.test_order_calculator_bot.entity;

import com.example.test_order_calculator_bot.config.CoefficientsForOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BotUserOrder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String currency;
    private String sumIncoming;
    private String risk;
    private String balance;
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "bot_user_id")
    private BotUser botUser;

    public Double computeOrder() {
        try {
            return Double.parseDouble(balance) / CoefficientsForOrder.coefficients.get(currency) *
                            (Double.parseDouble(risk) / 100) +
                            Double.parseDouble(sumIncoming);
        } catch (Exception e) {
            return null;
        }
    }
}
