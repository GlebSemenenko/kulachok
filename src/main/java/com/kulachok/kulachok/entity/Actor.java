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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Embedded
    private FullName nameActor;

    @Column(name = "age")
    private int age;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "followers")
    private int followers;

    @Column(name = "registrationDate")
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "actor")
    private List<Subscription> subscriptions;
}
