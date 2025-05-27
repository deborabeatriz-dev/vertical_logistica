package com.dev.vertical_logistica.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.dev.vertical_logistica.model.Order;
import com.dev.vertical_logistica.model.Product;
import com.dev.vertical_logistica.model.User;
import com.dev.vertical_logistica.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final ProductService productService;

    public Order findOrCreateOrder(Long orderId, LocalDate date, User user) {
        try {
            log.info("Buscando pedido com ID: {}", orderId);
            return orderRepository.findById(orderId).orElseGet(() -> {
                Order newOrder = new Order(orderId, date, new ArrayList<>(), user);
                Order savedOrder = orderRepository.save(newOrder);
                log.info("Pedido criado e salvo com sucesso: {}", savedOrder.getOrderId());
                return savedOrder;
            });
        } catch (Exception e) {
            log.error("Erro ao buscar ou criar pedido ID: {}", orderId, e);
            throw new RuntimeException("Falha ao buscar ou criar pedido", e);
        }
    }

    public void addProductToOrder(Order order, Long productId, BigDecimal price) {
        try {
            log.info("Adicionando produto ao pedido. orderId={}, productId={}, price={}", order.getOrderId(), productId, price);
            Product product = productService.createProduct(productId, price, order);

            if (!order.getProducts().contains(product)) {
                order.getProducts().add(product);
            }

            orderRepository.save(order);
            log.info("Pedido atualizado salvo no banco: {}", order.getOrderId());

        } catch (Exception e) {
            log.error("Erro ao adicionar produto ao pedido ID: {}", order.getOrderId(), e);
            throw new RuntimeException("Falha ao adicionar produto ao pedido", e);
        }
    }
}
