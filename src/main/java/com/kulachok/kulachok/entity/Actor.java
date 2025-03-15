package com.kulachok.kulachok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kulachok.kulachok.entity.model_interface.CashAccountHolder;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
    private Integer id;

    /**
     * Полное имя актера.
     */
    @Embedded
    private FullName nameActor;

    /**
     * Возраст актера.
     */
    @Column(name = "age")
    private Integer age;

    /**
     * Национальность актера.
     */
    @Column(name = "nationality")
    private String nationality;

    /**
     * Количество подписчиков актера.
     */
    @Column(name = "followers")
    private Integer followers;

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

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
