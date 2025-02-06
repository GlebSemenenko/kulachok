package com.kulachok.kulachok.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int transactionId;

    @Column(name = "transactionDate", nullable = false)
    LocalDateTime transactionDate;

    @Column(name = "createdAt", nullable = false)
    LocalDateTime createdAt;

    @Column(name = "updatedAt", nullable = false)
    LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actrisId", nullable = false)
    private Actris actris;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashId", nullable = false)
    private Cash cash;

}
