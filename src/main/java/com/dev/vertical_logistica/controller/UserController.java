package com.dev.vertical_logistica.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/getUsers")
    public List<UserDto> getUsersWithFilters(
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {
            var filteredOrders = user.getOrderUsers().stream()
                    .filter(order -> orderId == null || order.getOrderId().equals(orderId))
                    .filter(order -> {
                        if (startDate == null && endDate == null)
                            return true;
                        LocalDate orderDate = order.getDate();
                        return (startDate == null || !orderDate.isBefore(startDate)) &&
                                (endDate == null || !orderDate.isAfter(endDate));
                    })
                    .map(orderUser -> {
                        var productOrders = orderUser.getProductOrders().stream()
                                .map(productOrder -> new ProducOrdertDto(productOrder.getProductId(),
                                        productOrder.getPrice()))
                                .toList();

                        return new OrderUserDto(orderUser.getOrderId(), orderUser.getTotal(), orderUser.getDate(),
                                productOrders);
                    }).toList();

            return new UserDto(user.getUserId(), user.getName(), filteredOrders);
        }).filter(userDto -> !userDto.getOrdersUsers().isEmpty()).toList();
    }
}
