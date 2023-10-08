package com.example.test_order_calculator_bot.config;

import java.util.HashMap;
import java.util.Map;

public final class CoefficientsForOrder {
    public static final Map<String, Double> coefficients = new HashMap<>() {{
        put("Рубли", 10.0);
        put("Доллары", 7.0);
        put("Евро", 30.0);
    }};
}
