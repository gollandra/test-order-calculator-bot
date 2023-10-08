package com.example.test_order_calculator_bot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String currency;
    private String sumIncoming;
    private String risk;
    private String balance;

    public Double computeOrder() {
        switch (currency) {
            case "Рубли":
                try {
                    return Double.parseDouble(balance) / 10 *
                            (Double.parseDouble(risk) / 100) +
                            Double.parseDouble(sumIncoming);
                } catch (Exception e) {
                    return null;
                }
            case "Доллары":
                try {
                    return Double.parseDouble(balance) / 7 *
                            (Double.parseDouble(risk) / 100) +
                            Double.parseDouble(sumIncoming);
                } catch (Exception e) {
                    return null;
                }
            case "Евро":
                try {
                    return Double.parseDouble(balance) / 30 *
                            (Double.parseDouble(risk) / 100) +
                            Double.parseDouble(sumIncoming);
                } catch (Exception e) {
                    return null;
                }
        }
        return null;
    }
}
