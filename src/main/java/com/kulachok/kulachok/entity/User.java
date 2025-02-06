package com.kulachok.kulachok.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * User: Хранит данные пользователей
     * (идентификатор, имя, дата регистрации, возраст, электронная почта, ссылка на кошелек).
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "registration_date", nullable = false)
    LocalDate registrationDate;

    @Column(name = "age", nullable = false)
    int age;

    @Column(name = "cash_account", nullable = false)
    BigInteger cashAccount;

    @Column(name = "subscriptions")
    String subscriptions;

    @Column(name = "email")
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private Cash cash;

    @ManyToMany(mappedBy = "subscribedActresses")
    private List<User> subscribers;

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "actrisId")
    )
    private List<Actris> subscribedActresses;

}
