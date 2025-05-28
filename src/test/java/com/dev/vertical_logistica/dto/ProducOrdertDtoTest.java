package com.dev.vertical_logistica.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
public class ProducOrdertDtoTest {

    @Test
    void shouldCreateProducOrdertDto() {
        BigDecimal price = new BigDecimal("99.99");
        ProducOrdertDto dto = new ProducOrdertDto(1L, price);

        assertEquals(1L, dto.getProductId());
        assertEquals(price, dto.getPrice());
    }

    @Test
    void shouldSetAndGetProperties() {
        ProducOrdertDto dto = new ProducOrdertDto();
        BigDecimal price = new BigDecimal("149.99");

        dto.setProductId(5L);
        dto.setPrice(price);

        assertEquals(5L, dto.getProductId());
        assertEquals(price, dto.getPrice());
    }
}
