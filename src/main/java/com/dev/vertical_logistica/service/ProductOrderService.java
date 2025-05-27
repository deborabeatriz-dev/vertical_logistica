package com.dev.vertical_logistica.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.dev.vertical_logistica.model.OrderUser;
import com.dev.vertical_logistica.model.ProductOrder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductOrderService {
    
    public ProductOrder createProductOrder(Long productId, BigDecimal price, OrderUser orderUser) {
        try {
            log.info("Criando produto. productId={}, price={}, orderId={}", productId, price, orderUser.getOrderId());
            return new ProductOrder(null, productId, price, orderUser);
        } catch (Exception e) {
            log.error("Erro ao criar produto productId={}, orderId={}", productId, orderUser.getOrderId(), e);
            throw new RuntimeException("Falha ao criar produto", e);
        }
    }
    
}
