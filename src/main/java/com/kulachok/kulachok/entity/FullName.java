package com.kulachok.kulachok.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FullName {
    /**
     * Псевдоним пользователя.
     */
    private String nickname;

    /**
     * Имя пользователя.
     */
    private String firstName;

    /**
     * Отчество пользователя.
     */
    private String middleName;

    /**
     * Фамилия пользователя.
     */
    private String lastName;

    @Override
    public String toString() {
        return nickname + " " + firstName + " " + middleName + " " + lastName;
    }


}
