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

    // Пустой конструктор для JPA
    public Cash() {
    }

    // Полный конструктор
    public Cash(BigDecimal amount, String description, String transferType, LocalDateTime transferDate, User user, Actris actris) {
        this.amount = amount;
        this.description = description;
        this.transferType = transferType;
        this.transferDate = transferDate;
        this.user = user;
        this.actris = actris;
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getTransferType() {
        return transferType;
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

    public List<Transfer> getTransfers() {
        return transfers;
    }

    // Сеттеры
    public void setId(int id) {
        this.id = id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
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

    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }
}