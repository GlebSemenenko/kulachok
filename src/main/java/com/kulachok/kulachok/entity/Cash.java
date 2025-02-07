package com.kulachok.kulachok.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private Long id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Column(name = "transactionDate", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "transactionType", nullable = false)
    private String transactionType;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToOne
    @JoinColumn(name = "actris_id", unique = true)
    private Actris actris;

    @OneToMany(mappedBy = "cashAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;
}
