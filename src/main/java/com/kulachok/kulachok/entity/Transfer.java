package com.kulachok.kulachok.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {

    /**
     * Transfer: хранение данных о переводах
     * Поля: (Идентификатор транзакции, опиание, дата)
     * Связи: (MTO User, MTO Actris, MTO Cash)
     */

    //TODO переделать на long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Description")
    private String description;

    @Column(name = "TransferDate", nullable = false)
    private LocalDateTime transferDate = LocalDateTime.now();

    @Column(name = "SumTransfer")
    private BigDecimal sumTransfer;

    //todo -> изменение названия на пользователя
    @Column(name = "AllSumTransfer")
    private BigDecimal AllSumTransfer;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ActrissID")
    private Actris actris;

    @ManyToOne
    @JoinColumn(name = "CashID", nullable = false)
    private Cash cashAccount;
}
