package com.dev.vertical_logistica.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dev.vertical_logistica.dto.OrderUserDto;
import com.dev.vertical_logistica.dto.ProducOrdertDto;
import com.dev.vertical_logistica.dto.UserDto;
import com.dev.vertical_logistica.model.OrderUser;
import com.dev.vertical_logistica.model.User;
import com.dev.vertical_logistica.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OrderUserService orderService;

    public void processUserOrderProduct(Long userId, String name, Long orderId, LocalDate date, Long productId,
            BigDecimal price) {
        try {
            log.info("Processando dados: userId={}, name={}, orderId={}, date={}, productId={}, price={}", userId, name, orderId, date, productId, price);

            User user = userRepository.findById(userId)
                .orElseGet(() -> {
                    log.info("Usuário não encontrado. Criando novo usuário ID: {}, name: {}", userId, name);
                    User newUser = new User(userId, name, new ArrayList<>());
                    User savedUser = userRepository.save(newUser);
                    log.info("Usuário criado e salvo: {}", savedUser.getUserId());
                    return savedUser;
                });

            OrderUser orderUser = orderService.createOrderUser(orderId, date, user);
            orderService.addProductToOrderUser(orderUser, productId, price);

            if (!user.getOrderUsers().contains(orderUser)) {
                user.getOrderUsers().add(orderUser);
                log.info("Pedido adicionado à lista de pedidos do usuário ID: {}", userId);
            } else {
                log.info("Pedido já está na lista do usuário ID: {}", userId);
            }

            userRepository.save(user);
            log.info("Usuário atualizado salvo no banco: {}", userId);

        } catch (Exception e) {
            log.error("Erro ao processar userId={}, orderId={}, productId={}", userId, orderId, productId, e);
            throw new RuntimeException("Falha no processamento dos dados do usuário/pedido/produto", e);
        }
    }

    public List<UserDto> getUsersWithFilters(Long orderId, LocalDate startDate, LocalDate endDate) {
        try {
            log.info("Buscando usuários com filtros: orderId={}, startDate={}, endDate={}", orderId, startDate, endDate);
            
            List<User> users = userRepository.findAll();
            log.info("Total de usuários encontrados: {}", users.size());

            List<UserDto> result = users.stream().map(user -> {
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

            log.info("Usuários filtrados retornados: {}", result.size());
            return result;
            
        } catch (Exception e) {
            log.error("Erro ao buscar usuários com filtros: orderId={}, startDate={}, endDate={}", orderId, startDate, endDate, e);
            throw new RuntimeException("Falha ao buscar usuários com filtros", e);
        }
    }

}
