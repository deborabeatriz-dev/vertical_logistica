package com.dev.vertical_logistica.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderUserDto {

    @JsonProperty("order_id")
    private Long orderId;

    private BigDecimal total;

    private LocalDate date;

    private List<ProducOrdertDto> productsOrders;

}
