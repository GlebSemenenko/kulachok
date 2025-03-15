package com.kulachok.kulachok.entity;

import com.kulachok.kulachok.entity.model_interface.CashAccountHolder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements CashAccountHolder {
    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Имя пользователя.
     */
    @Column(name = "username")
    private String username;

    /**
     * Возраст пользователя.
     */
    @Column(name = "age")
    private Integer age;

    /**
     * Адрес электронной почты пользователя.
     */
    @Column(name = "email")
    private String email;

    /**
     * Пароль пользователя.
     */
    @Column(name = "password")
    private String password;

    /**
     * Дата регистрации пользователя.
     */
    @Column(name = "registrationDate")
    private LocalDate registrationDate = LocalDate.now();

    /**
     * Объект, представляющий актера, связанного с пользователем.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Actor actor;

    /**
     * Список подписок пользователя на актрис.
     */
    @OneToMany(mappedBy = "user")
    private List<Subscription> subscriptions;
}
