package com.dev.vertical_logistica.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.dev.vertical_logistica.model.Order;
import com.dev.vertical_logistica.model.User;
import com.dev.vertical_logistica.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OrderService orderService;

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

            Order order = orderService.findOrCreateOrder(orderId, date, user);
            orderService.addProductToOrder(order, productId, price);

            if (!user.getOrders().contains(order)) {
                user.getOrders().add(order);
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

}
