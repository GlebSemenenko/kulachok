package com.kulachok.kulachok.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Actris {

    @Id
    int id;

    String namebigint;

    int followers;

    int age;

    String nationality;




}
