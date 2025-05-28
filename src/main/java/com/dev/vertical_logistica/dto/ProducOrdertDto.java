package com.dev.vertical_logistica.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducOrdertDto {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("value")
    private BigDecimal price;
    
}
