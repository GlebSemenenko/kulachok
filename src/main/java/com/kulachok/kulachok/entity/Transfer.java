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
@AllArgsConstructor
public class Transfer {

    /**
     * Transfer: хранение данных о переводах
     * Поля: (Идентификатор транзакции, опиание, дата)
     * Связи: (MTO User, MTO Actris, MTO Cash)
     */

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

    // Конструкторы
    public Transfer() {
    }

    public Transfer(String description, LocalDateTime transferDate, User user, Actris actris, Cash cashAccount) {
        this.description = description;
        this.transferDate = transferDate;
        this.user = user;
        this.actris = actris;
        this.cashAccount = cashAccount;
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTransferDate() {
        return transferDate;
    }

    public User getUser() {
        return user;
    }

    public Actris getActris() {
        return actris;
    }

    public Cash getCashAccount() {
        return cashAccount;
    }

    // Сеттеры
    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransferDate(LocalDateTime transferDate) {
        this.transferDate = transferDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setActris(Actris actris) {
        this.actris = actris;
    }

    public void setCashAccount(Cash cashAccount) {
        this.cashAccount = cashAccount;
    }

}
