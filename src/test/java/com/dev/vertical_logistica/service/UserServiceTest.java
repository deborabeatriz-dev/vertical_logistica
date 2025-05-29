package com.dev.vertical_logistica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dev.vertical_logistica.dto.UserDto;
import com.dev.vertical_logistica.model.OrderUser;
import com.dev.vertical_logistica.model.ProductOrder;
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

    private User createTestUser(Long userId, String name, Long orderId, LocalDate date) {
        User user = new User();
        user.setUserId(userId);
        user.setName(name);
        user.setOrderUsers(new ArrayList<>());

        OrderUser orderUser = new OrderUser();
        orderUser.setOrderId(orderId);
        orderUser.setDate(date);
        orderUser.setUser(user);
        orderUser.setProductOrders(new ArrayList<>());

        ProductOrder productOrder = new ProductOrder();
        productOrder.setProductId(1L);
        productOrder.setPrice(new BigDecimal("100.00"));
        productOrder.setOrderUser(orderUser);

        orderUser.getProductOrders().add(productOrder);
        user.getOrderUsers().add(orderUser);

        return user;
    }

    @Test
    void shouldGetUsersWithFiltersSuccessfully() {
        User user1 = createTestUser(1L, "João", 100L, LocalDate.of(2025, 5, 15));
        User user2 = createTestUser(2L, "Maria", 200L, LocalDate.of(2025, 6, 10));
        
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDto> result = userService.getUsersWithFilters(null, null, null);

        assertEquals(2, result.size());
        assertEquals("João", result.get(0).getName());
        assertEquals("Maria", result.get(1).getName());
    }

    @Test
    void shouldFilterUsersByOrderId() {
        User user1 = createTestUser(1L, "João", 100L, LocalDate.of(2025, 5, 15));
        User user2 = createTestUser(2L, "Maria", 200L, LocalDate.of(2025, 6, 10));
        
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDto> result = userService.getUsersWithFilters(100L, null, null);

        assertEquals(1, result.size());
        assertEquals("João", result.get(0).getName());
        assertEquals(100L, result.get(0).getOrdersUsers().get(0).getOrderId());
    }

    @Test
    void shouldFilterUsersByDateRange() {
        User user1 = createTestUser(1L, "João", 100L, LocalDate.of(2025, 5, 15));
        User user2 = createTestUser(2L, "Maria", 200L, LocalDate.of(2025, 6, 10));
        
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDto> result = userService.getUsersWithFilters(null, 
            LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 31));

        assertEquals(1, result.size());
        assertEquals("João", result.get(0).getName());
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersMatchFilters() {
        User user1 = createTestUser(1L, "João", 100L, LocalDate.of(2025, 5, 15));
        
        when(userRepository.findAll()).thenReturn(List.of(user1));

        List<UserDto> result = userService.getUsersWithFilters(999L, null, null);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldHandleExceptionInGetUsersWithFilters() {
        when(userRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> userService.getUsersWithFilters(null, null, null));

        assertEquals("Falha ao buscar usuários com filtros", exception.getMessage());
    }

}
