package com.dev.vertical_logistica.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "date")
    private LocalDate date;

    @OneToMany
    @Column(name = "products")
    private List<Product> products;
    

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
}
