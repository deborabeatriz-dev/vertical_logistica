package com.dev.vertical_logistica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.dev.vertical_logistica.model.OrderUser;
import com.dev.vertical_logistica.model.ProductOrder;

public class ProductOrderServiceTest {

    private ProductOrderService productOrderService = new ProductOrderService();

    @Test
    void shouldCreateProductOrder() {
        OrderUser orderUser = new OrderUser();
        Long productId = 1L;
        BigDecimal price = new BigDecimal("19.99");

        ProductOrder result = productOrderService.createProductOrder(productId, price, orderUser);

        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        assertEquals(price, result.getPrice());
        assertEquals(orderUser, result.getOrderUser());
    }
}
