package com.kulachok.kulachok.entity;

import com.kulachok.kulachok.entity.model_Interface.CashAccountHolder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "actors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Actor implements CashAccountHolder {
    /**
     * Уникальный идентификатор актера.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Полное имя актера.
     */
    @Embedded
    private FullName nameActor;

    /**
     * Возраст актера.
     */
    @Column(name = "age")
    private int age;

    /**
     * Национальность актера.
     */
    @Column(name = "nationality")
    private String nationality;

    /**
     * Количество подписчиков актера.
     */
    @Column(name = "followers")
    private int followers;

    /**
     * Дата регистрации актера.
     */
    @Column(name = "registrationDate")
    private LocalDate registrationDate;

    /**
     * Список подписок на актера.
     */
    @OneToMany(mappedBy = "actor")
    private List<Subscription> subscriptions;
}
