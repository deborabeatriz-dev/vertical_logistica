package com.dev.vertical_logistica.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    
    @JsonProperty("user_id")
    private Long userId;

    private String name;

    @JsonProperty("orders")
    private List<OrderUserDto> ordersUsers;
    
}
