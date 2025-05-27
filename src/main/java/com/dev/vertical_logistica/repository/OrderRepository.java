package com.dev.vertical_logistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.vertical_logistica.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
