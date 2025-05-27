package com.dev.vertical_logistica.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.dev.vertical_logistica.model.Order;
import com.dev.vertical_logistica.model.Product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    
    public Product createProduct(Long productId, BigDecimal price, Order order) {
        try {
            log.info("Criando produto. productId={}, price={}, orderId={}", productId, price, order.getOrderId());
            return new Product(productId, price, order);
        } catch (Exception e) {
            log.error("Erro ao criar produto productId={}, orderId={}", productId, order.getOrderId(), e);
            throw new RuntimeException("Falha ao criar produto", e);
        }
    }
}
