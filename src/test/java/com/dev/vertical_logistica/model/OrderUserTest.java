package com.dev.vertical_logistica.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class OrderUserTest {

    @Test
    void shouldReturnZeroWhenProductOrdersIsNull() {
        OrderUser orderUser = new OrderUser(1L, 100L, LocalDate.now(), null, new User());
        assertEquals(BigDecimal.ZERO, orderUser.getTotal());
    }

    @Test
    void shouldReturnZeroWhenProductOrdersIsEmpty() {
        OrderUser orderUser = new OrderUser(1L, 100L, LocalDate.now(), new ArrayList<>(), new User());
        assertEquals(BigDecimal.ZERO, orderUser.getTotal());
    }

    @Test
    void shouldReturnTotalOfAllProductPrices() {
        OrderUser orderUser = new OrderUser(1L, 100L, LocalDate.now(), new ArrayList<>(), new User());

        ProductOrder p1 = new ProductOrder(null, 1L, new BigDecimal("50.00"), orderUser);
        ProductOrder p2 = new ProductOrder(null, 2L, new BigDecimal("75.00"), orderUser);
        ProductOrder p3 = new ProductOrder(null, 3L, new BigDecimal("25.00"), orderUser);

        orderUser.getProductOrders().add(p1);
        orderUser.getProductOrders().add(p2);
        orderUser.getProductOrders().add(p3);

        assertEquals(new BigDecimal("150.00"), orderUser.getTotal());
    }
}
