package com.dev.vertical_logistica.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "date")
    private LocalDate date;

    @OneToMany(mappedBy = "orderUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOrder> productOrders = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    public BigDecimal getTotal() {
        if (productOrders == null || productOrders.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return productOrders.stream()
                    .map(ProductOrder::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
