package com.kulachok.kulachok.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transfer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

    /**
     * Transfer: хранение данных о переводах
     * Поля: (Идентификатор транзакции, опиание, дата)
     * Связи: (MTO User, MTO Actris, MTO Cash)
     */

    public Transfer(String description, int userId, int cashId) {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Description")
    private String description;

    @Column(name = "TransferDate", nullable = false)
    private LocalDateTime transferDate = LocalDateTime.now();

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
