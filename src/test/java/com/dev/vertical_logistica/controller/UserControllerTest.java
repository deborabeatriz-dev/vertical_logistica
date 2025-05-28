package com.dev.vertical_logistica.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.dev.vertical_logistica.dto.OrderUserDto;
import com.dev.vertical_logistica.dto.ProducOrdertDto;
import com.dev.vertical_logistica.dto.UserDto;
import com.dev.vertical_logistica.model.OrderUser;
import com.dev.vertical_logistica.model.ProductOrder;
import com.dev.vertical_logistica.model.User;
import com.dev.vertical_logistica.repository.UserRepository;

public class UserControllerTest {

    private UserRepository userRepository;
    private UserController userController;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        userController = new UserController(userRepository);
    }

    @Test
    void testGetUsersWithFilters_returnsFilteredUserDtos() {
        
        User user = new User();
        user.setUserId(1L);
        user.setName("Débora");

        OrderUser orderUser = new OrderUser();
        orderUser.setOrderId(100L);
        orderUser.setDate(LocalDate.of(2025, 5, 28));
        orderUser.setUser(user);

        ProductOrder productOrder = new ProductOrder();
        productOrder.setProductId(1L);
        productOrder.setPrice(new BigDecimal("500.00"));
        productOrder.setOrderUser(orderUser);

        orderUser.setProductOrders(List.of(productOrder));
        user.setOrderUsers(List.of(orderUser));

        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDto> result = userController.getUsersWithFilters(
                100L,
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 31)
        );

        assertEquals(1, result.size());
        UserDto userDto = result.get(0);
        assertEquals(1L, userDto.getUserId());
        assertEquals("Débora", userDto.getName());
        assertEquals(1, userDto.getOrdersUsers().size());

        OrderUserDto orderDto = userDto.getOrdersUsers().get(0);
        assertEquals(100L, orderDto.getOrderId());
        assertEquals(new BigDecimal("500.00"), orderDto.getTotal());
        assertEquals(LocalDate.of(2025, 5, 28), orderDto.getDate());
        assertEquals(1, orderDto.getProductsOrders().size());

        ProducOrdertDto productDto = orderDto.getProductsOrders().get(0);
        assertEquals(1L, productDto.getProductId());
        assertEquals(new BigDecimal("500.00"), productDto.getPrice());
    }

    @Test
    void testGetUsersWithFilters_noMatchingOrders_returnsEmptyList() {

        User user = new User();
        user.setUserId(1L);
        user.setName("Débora");

        OrderUser orderUser = new OrderUser();
        orderUser.setOrderId(200L);
        orderUser.setDate(LocalDate.of(2025, 1, 1));
        user.setOrderUsers(List.of(orderUser));

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDto> result = userController.getUsersWithFilters(100L, null, null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
