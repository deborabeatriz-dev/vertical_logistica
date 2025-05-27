package com.dev.vertical_logistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.vertical_logistica.model.OrderUser;

public interface OrderUserRepository extends JpaRepository<OrderUser, Long> {
    
}
