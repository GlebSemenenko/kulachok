package com.kulachok.kulachok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@NoArgsConstructor
public class Actris implements CashAccountHolder {

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

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "followers", nullable = false)
    private int followers;

    @Column(name = "registrationDate", nullable = false)
    private LocalDate registrationDate = LocalDate.now();

    @JsonIgnore
    @OneToOne(mappedBy = "actris", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cash cashAccount;

    @JsonIgnore
    @OneToMany(mappedBy = "actris", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSubscription> subscriptions;

}
