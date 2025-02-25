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
@Table(name = "actriss")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Actris implements CashAccountHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "followers")
    private int followers;

    @Column(name = "registrationDate")
    private LocalDate registrationDate = LocalDate.now();

    @OneToMany(mappedBy = "actris")
    private List<UserSubscription> subscriptions;
}
