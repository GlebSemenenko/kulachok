package com.kulachok.kulachok.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class UserDto {

    private Integer id;
    private String name;
    private LocalDate registrationDate;
    private Integer age;
    private String cashAccount;
    private String subscriptions;
}