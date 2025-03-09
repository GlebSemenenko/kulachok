package com.kulachok.kulachok.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Subscription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    /**
     * Уникальный идентификатор подписки.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор пользователя.
     */
    @Column(name = "UserID", nullable = false)
    private Long userId;

    /**
     * Идентификатор актрисы.
     */
    @Column(name = "ActrisID", nullable = false)
    private Long actrisId;

    /**
     * Дата и время подписки.
     */
    @Column(name = "SubscriptionDate")
    private LocalDateTime subscriptionDate = LocalDateTime.now();

    /**
     * Связанный объект пользователя.
     */
    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    /**
     * Связанный объект актрисы.
     */
    @ManyToOne
    @JoinColumn(name = "ActrisID", insertable = false, updatable = false)
    private Actor actor;
}
