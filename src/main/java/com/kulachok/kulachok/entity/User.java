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
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * User: Хранит данные пользователей
     * Поля: (идентификатор, имя, дата регистрации, возраст, электронная почта, ссылка на кошелек).
     * Связи: (OTO CashAccount, OTM UserSubscription)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "registrationDate", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "email", unique = true)
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cash cashAccount;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSubscription> subscriptions;

}
