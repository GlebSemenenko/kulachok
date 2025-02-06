package com.kulachok.kulachok.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Cash")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cash {

    /**
     Cash: Хранит данные о денежных операциях
     (идентификатор, сумма, описание, дата, тип транзакции).
     Представляет собой кошельки пользователей и актрис.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "amount", nullable = false)
    BigDecimal amount;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "transactionData", nullable = false)
    LocalDateTime transactionData;

    @Column(name = "transactionType", nullable = false)
    String transactionType;

    @Column(name = "createdAt", nullable = false)
    LocalDateTime createdAt;

    @Column(name = "updatedAt", nullable = false)
    LocalDateTime updatedAt;

    @OneToOne(mappedBy = "cash", cascade = CascadeType.ALL, orphanRemoval = true)
    private Transaction transaction;

    @OneToOne
    @JoinColumn(name = "userId", unique = true, nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "actrisId", unique = true, nullable = false)
    private Actris actris;
}
