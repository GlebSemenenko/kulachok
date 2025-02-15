package com.kulachok.kulachok.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "actriss")
@Getter
@Setter
@AllArgsConstructor
public class Actris {

    /**
     * Actris: Хранит данные актрис
     * Поля: (идентификатор, имя, подписчики, возраст, национальность, ссылка на кошелек).
     * Связи: (OTO CashAccount, OTM UserSubscription)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "followers", nullable = false)
    private int followers;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "registrationDate", nullable = false)
    private LocalDate registrationDate = LocalDate.now();

    @OneToOne(mappedBy = "actris", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cash cashAccount;

    @OneToMany(mappedBy = "actris", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSubscription> subscriptions;

    // Конструктор по умолчанию (нужен для Hibernate)
    public Actris() {
    }

    // Полный конструктор
    public Actris(String name, int followers, int age, String nationality, LocalDate registrationDate) {
        this.name = name;
        this.followers = followers;
        this.age = age;
        this.nationality = nationality;
        this.registrationDate = registrationDate;
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFollowers() {
        return followers;
    }

    public int getAge() {
        return age;
    }

    public String getNationality() {
        return nationality;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public Cash getCashAccount() {
        return cashAccount;
    }

    public List<UserSubscription> getSubscriptions() {
        return subscriptions;
    }

    // Сеттеры
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setCashAccount(Cash cashAccount) {
        this.cashAccount = cashAccount;
    }

    public void setSubscriptions(List<UserSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

}
