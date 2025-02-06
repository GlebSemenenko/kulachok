package com.kulachok.kulachok.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    String name;

    int followers;

    int age;

    String nationality;

    BigDecimal cashAccount;

    String biography;

    String profilePictureUrl;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
