package com.dev.vertical_logistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.vertical_logistica.model.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    
}
