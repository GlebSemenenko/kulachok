package com.kulachok.kulachok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
public class User {

    /**
     * User: Хранит данные пользователей
     * Поля: (идентификатор, имя, дата регистрации, возраст, электронная почта, ссылка на кошелек).
     * Связи: (OTO CashAccount, OTM UserSubscription)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false, unique = true) // Добавлено поле
    private String username;

    @Column(name = "registrationDate")
    private LocalDate registrationDate = LocalDate.now();

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "email", unique = true)
    private String email;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cash cashAccount;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSubscription> subscriptions;

    // --- Конструкторы ---
    public User() {}

    public User(String username, int age, String email) {
        this.username = username;
        this.age = age;
        this.email = email;
    }

    public User(int id, String username, LocalDate registrationDate, int age, String email, Cash cashAccount, List<UserSubscription> subscriptions) {
        this.id = id;
        this.username = username;
        this.registrationDate = registrationDate;
        this.age = age;
        this.email = email;
        this.cashAccount = cashAccount;
        this.subscriptions = subscriptions;
    }

    // --- Геттеры ---
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public Cash getCashAccount() {
        return cashAccount;
    }

    public List<UserSubscription> getSubscriptions() {
        return subscriptions;
    }

    // --- Сеттеры ---
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCashAccount(Cash cashAccount) {
        this.cashAccount = cashAccount;
    }

    public void setSubscriptions(List<UserSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

}
