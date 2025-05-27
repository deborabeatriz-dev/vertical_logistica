package com.dev.vertical_logistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.vertical_logistica.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
