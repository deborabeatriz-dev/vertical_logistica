package com.dev.vertical_logistica.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class UserDtoTest {

    @Test
    void shouldCreateUserDto() {
        List<OrderUserDto> orders = new ArrayList<>();
        UserDto dto = new UserDto(1L, "Test User", orders);

        assertEquals(1L, dto.getUserId());
        assertEquals("Test User", dto.getName());
        assertEquals(orders, dto.getOrdersUsers());
    }

    @Test
    void shouldSetAndGetProperties() {
        UserDto dto = new UserDto();
        List<OrderUserDto> orders = new ArrayList<>();

        dto.setUserId(2L);
        dto.setName("Another User");
        dto.setOrdersUsers(orders);

        assertEquals(2L, dto.getUserId());
        assertEquals("Another User", dto.getName());
        assertEquals(orders, dto.getOrdersUsers());
    }
}
