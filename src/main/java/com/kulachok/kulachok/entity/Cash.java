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
     * Cash: Хранит данные о денежных операциях
     * Поля: (идентификатор, сумма, описание, дата, тип транзакции).
     * Связи: (OTO User, OTO Actris, OTM Ttransfer)
     */

    public Cash(String description, String transferType, int id) {
        this.description = description;
        this.transferType = transferType;
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Column(name = "transferType")
    private String transferType;

    @Column(name = "transferDate")
    private LocalDateTime transferDate = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToOne
    @JoinColumn(name = "actris_id", unique = true)
    private Actris actris;

    @OneToMany(mappedBy = "cashAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transfer> transfers;
}