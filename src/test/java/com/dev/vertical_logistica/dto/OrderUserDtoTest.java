package com.dev.vertical_logistica.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class OrderUserDtoTest {

    @Test
    void shouldCreateOrderUserDto() {
        List<ProducOrdertDto> products = new ArrayList<>();
        LocalDate date = LocalDate.now();
        BigDecimal total = new BigDecimal("100.00");

        OrderUserDto dto = new OrderUserDto(1L, total, date, products);

        assertEquals(1L, dto.getOrderId());
        assertEquals(total, dto.getTotal());
        assertEquals(date, dto.getDate());
        assertEquals(products, dto.getProductsOrders());
    }

    @Test
    void shouldSetAndGetProperties() {
        OrderUserDto dto = new OrderUserDto();
        List<ProducOrdertDto> products = new ArrayList<>();
        LocalDate date = LocalDate.of(2025, 5, 28);
        BigDecimal total = new BigDecimal("250.50");

        dto.setOrderId(10L);
        dto.setTotal(total);
        dto.setDate(date);
        dto.setProductsOrders(products);

        assertEquals(10L, dto.getOrderId());
        assertEquals(total, dto.getTotal());
        assertEquals(date, dto.getDate());
        assertEquals(products, dto.getProductsOrders());
    }
}
