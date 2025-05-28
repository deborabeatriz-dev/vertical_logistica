package com.dev.vertical_logistica.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUserDto {

    @JsonProperty("order_id")
    private Long orderId;

    private BigDecimal total;

    private LocalDate date;

    @JsonProperty("products")
    private List<ProducOrdertDto> productsOrders;

}
