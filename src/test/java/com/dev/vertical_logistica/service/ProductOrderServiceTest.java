package com.dev.vertical_logistica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void shouldThrowExceptionWhenOrderUserFails() {
        OrderUser orderUser = new OrderUser() {
            @Override
            public Long getOrderId() {
                throw new RuntimeException("Erro simulado");
            }
        };

        Long productId = 1L;
        BigDecimal price = new BigDecimal("19.99");

        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
            productOrderService.createProductOrder(productId, price, orderUser)
        );

        assertEquals("Falha ao criar produto", thrown.getMessage());
        assertNotNull(thrown.getCause());
        assertEquals("Erro simulado", thrown.getCause().getMessage());
    }
}
