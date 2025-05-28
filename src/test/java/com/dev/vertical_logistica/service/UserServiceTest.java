package com.dev.vertical_logistica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dev.vertical_logistica.model.OrderUser;
import com.dev.vertical_logistica.model.User;
import com.dev.vertical_logistica.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderUserService orderUserService;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldProcessNewUserAndCreateOrder() {
        Long userId = 1L;
        String name = "Dev";
        Long orderId = 10L;
        LocalDate date = LocalDate.now();
        Long productId = 100L;
        BigDecimal price = new BigDecimal("50.00");

        User user = new User(userId, name, new ArrayList<>());
        OrderUser orderUser = new OrderUser(1L, orderId, date, new ArrayList<>(), user);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);
        when(orderUserService.createOrderUser(orderId, date, user)).thenReturn(orderUser);

        userService.processUserOrderProduct(userId, name, orderId, date, productId, price);

        verify(userRepository, times(2)).save(any());
        verify(orderUserService).addProductToOrderUser(orderUser, productId, price);
    }

    @Test
    void shouldLogIfOrderAlreadyExistsInUser() {
        Long userId = 2L;
        String name = "Débora";
        Long orderId = 20L;
        LocalDate date = LocalDate.now();
        Long productId = 200L;
        BigDecimal price = new BigDecimal("75.00");

        User user = new User(userId, name, new ArrayList<>());
        OrderUser orderUser = new OrderUser(2L, orderId, date, new ArrayList<>(), user);
        user.getOrderUsers().add(orderUser);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(orderUserService.createOrderUser(orderId, date, user)).thenReturn(orderUser);

        userService.processUserOrderProduct(userId, name, orderId, date, productId, price);

        verify(orderUserService).addProductToOrderUser(orderUser, productId, price);
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenOrderCreationFails() {
        Long userId = 3L;
        String name = "Erro";
        Long orderId = 30L;
        LocalDate date = LocalDate.now();
        Long productId = 300L;
        BigDecimal price = new BigDecimal("100.00");

        User user = new User(userId, name, new ArrayList<>());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(orderUserService.createOrderUser(orderId, date, user))
            .thenThrow(new IllegalStateException("Erro simulado"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.processUserOrderProduct(userId, name, orderId, date, productId, price);
        });

        assertEquals("Falha no processamento dos dados do usuário/pedido/produto", thrown.getMessage());
    }

}
