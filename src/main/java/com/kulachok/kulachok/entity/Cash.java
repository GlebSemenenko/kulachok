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
    BigDecimal amount;
    String description;
    LocalDateTime transactionData;
    String transactionType;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
