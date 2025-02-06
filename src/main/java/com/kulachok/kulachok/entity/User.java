package com.kulachok.kulachok.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;

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


    @Column(name = "registration_date")
    LocalDate registrationDate;

    @Column(name = "age")
    int age;

    @Column(name = "cash_account", nullable = false)
    BigInteger cashAccount;

    @Column(name = "subscriptions", nullable = false)
    String subscriptions;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "password", nullable = false)
    String password;
}
