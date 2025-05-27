package com.dev.vertical_logistica.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.vertical_logistica.dto.OrderUserDto;
import com.dev.vertical_logistica.dto.ProducOrdertDto;
import com.dev.vertical_logistica.dto.UserDto;
import com.dev.vertical_logistica.model.User;
import com.dev.vertical_logistica.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserRepository userRepository;

    @GetMapping("/getAllUsers")
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {
            var orderUsers = user.getOrderUsers().stream().map(orderUser -> {
                var productOrders = orderUser.getProductOrders().stream().map(productOrder ->
                    new ProducOrdertDto(productOrder.getProductId(), productOrder.getPrice())
                ).toList();

                return new OrderUserDto(orderUser.getOrderId(), orderUser.getTotal(), orderUser.getDate(), productOrders);
            }).toList();

            return new UserDto(user.getUserId(), user.getName(), orderUsers);
        }).toList();
    }
}
