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
@Table(name = "actriss")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Actris {

    /**
     * Actris: Хранит данные актрис
     * (идентификатор, имя, подписчики, возраст, национальность, ссылка на кошелек).
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "followers", nullable = false)
    int followers;

    @Column(name = "age", nullable = false)
    int age;

    @Column(name = "nationality")
    String nationality;

    @OneToOne(mappedBy = "actris", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cash cashAccount;

    @OneToMany(mappedBy = "actris", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSubscription> subscriptions;

}
