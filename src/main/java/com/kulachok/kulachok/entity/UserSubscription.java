package com.kulachok.kulachok.entity;

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
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ActrisID", insertable = false, updatable = false)
    private Actris actris;

}


