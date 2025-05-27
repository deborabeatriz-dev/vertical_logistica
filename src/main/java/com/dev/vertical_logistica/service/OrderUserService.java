package com.dev.vertical_logistica.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.dev.vertical_logistica.model.OrderUser;
import com.dev.vertical_logistica.model.ProductOrder;
import com.dev.vertical_logistica.model.User;
import com.dev.vertical_logistica.repository.OrderUserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderUserService {
    
    private final OrderUserRepository orderRepository;
    private final ProductOrderService productOrderService;

    public OrderUser createOrderUser(Long orderId, LocalDate date, User user) {
        try {
            log.info("Criando novo pedido. orderId={}, userId={}", orderId, user.getUserId());
    
            OrderUser newOrderUser = new OrderUser(null, orderId, date, new ArrayList<>(), user);
            OrderUser savedOrderUser = orderRepository.save(newOrderUser);
    
            log.info("Pedido criado com sucesso: id={}, orderId={}", savedOrderUser.getId(), savedOrderUser.getOrderId());
            return savedOrderUser;
    
        } catch (Exception e) {
            log.error("Erro ao criar pedido orderId={}", orderId, e);
            throw new RuntimeException("Falha ao criar pedido", e);
        }
    }
    

    public void addProductToOrderUser(OrderUser orderUser, Long productId, BigDecimal price) {
        try {
            log.info("Adicionando produto ao pedido. orderId={}, productId={}, price={}", orderUser.getOrderId(), productId, price);
    
            ProductOrder productOrder = productOrderService.createProductOrder(productId, price, orderUser);
            orderUser.getProductOrders().add(productOrder);
            productOrder.setOrderUser(orderUser);
    
            orderRepository.save(orderUser);
            log.info("Pedido atualizado salvo no banco: {}", orderUser.getOrderId());
    
        } catch (Exception e) {
            log.error("Erro ao adicionar produto ao pedido ID: {}", orderUser.getOrderId(), e);
            throw new RuntimeException("Falha ao adicionar produto ao pedido", e);
        }
    }
    
}
