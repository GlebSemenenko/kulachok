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

    @Column(name = "Description")
    private String description;

    @Column(name = "transactionDate", nullable = false)
    LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ActrissID")
    private Actris actris;

    @ManyToOne
    @JoinColumn(name = "CashID", nullable = false)
    private Cash cash;

}
