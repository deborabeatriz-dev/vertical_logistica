package com.dev.vertical_logistica.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.vertical_logistica.model.OrderUser;
import com.dev.vertical_logistica.model.User;

public interface OrderUserRepository extends JpaRepository<OrderUser, Long> {
    
    Optional<OrderUser> findByOrderIdAndDateAndUser(Long orderId, LocalDate date, User user);

}
