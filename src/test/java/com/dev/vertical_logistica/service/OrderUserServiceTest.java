package com.dev.vertical_logistica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dev.vertical_logistica.model.OrderUser;
import com.dev.vertical_logistica.model.ProductOrder;
import com.dev.vertical_logistica.model.User;
import com.dev.vertical_logistica.repository.OrderUserRepository;

@ExtendWith(MockitoExtension.class)
public class OrderUserServiceTest {
    
    @Mock
    private OrderUserRepository orderUserRepository;
    
    @Mock 
    private ProductOrderService productOrderService;

    @InjectMocks
    private OrderUserService orderUserService;

    @Test
    void shouldReturnExistingOrderUser() {
        User user = new User (1L, "Dev", new ArrayList<>());
        OrderUser orderUser = new OrderUser(1L, 100L, LocalDate.now(), new ArrayList<>(), user);

        Mockito.when(orderUserRepository.findByOrderIdAndDateAndUser(100L, orderUser.getDate(), user))
            .thenReturn(Optional.of(orderUser));

        OrderUser result = orderUserService.createOrderUser(100L, orderUser.getDate(), user);
        
        assertEquals(orderUser, result);
        verify(orderUserRepository, never()).save(any());
    }

    @Test
    void shouldCreateNewOrderUserWhenNotExists(){
        User user = new User (1L, "Dev", new ArrayList<>());
        LocalDate date = LocalDate.now();

        Mockito.when(orderUserRepository.findByOrderIdAndDateAndUser(100L, date, user))
            .thenReturn(Optional.empty());

        Mockito.when(orderUserRepository.save(any(OrderUser.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        OrderUser result = orderUserService.createOrderUser(100L, date, user);

        assertEquals(100L, result.getOrderId());
        assertEquals(user, result.getUser());
    }

    @Test
    void shouldAddProductToOrderUserWhenNotExists(){
        OrderUser orderUser = new OrderUser(1L, 100L, LocalDate.now(), new ArrayList<>(), new User());
        ProductOrder productOrder = new ProductOrder(null, 200L, new BigDecimal("99.99"), orderUser);

        when(productOrderService.createProductOrder(any(), any(), any()))
            .thenReturn(productOrder);

        orderUserService.addProductToOrderUser(orderUser,200L, new BigDecimal("99.99"));

        assertEquals(1, orderUser.getProductOrders().size());
        verify(orderUserRepository).save(orderUser);
    }

}
