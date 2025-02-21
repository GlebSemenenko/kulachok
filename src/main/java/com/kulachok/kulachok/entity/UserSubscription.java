package com.kulachok.kulachok.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "userSubscriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSubscription {

    /**
     * UserSubscription: Табилица саязи подписаки Users на Actris
     * Поля: (Идентификатор, связь юзера, связь актрисы, время подписки)
     * Связи: (MTO User, MTO Actris)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "UserID", nullable = false)
    private Long userId;

    @Column(name = "ActrisID", nullable = false)
    private Long actrisId;

    @Column(name = "SubscriptionDate")
    private LocalDateTime subscriptionDate = LocalDateTime.now();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ActrisID", insertable = false, updatable = false)
    private Actris actris;

}


