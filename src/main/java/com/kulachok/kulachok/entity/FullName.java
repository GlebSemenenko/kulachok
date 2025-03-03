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
    private String nickname;
    private String firstName;
    private String middleName;
    private String lastName;

    @Override
    public String toString() {
        return nickname + " " + firstName + " " + middleName + " " + lastName;
    }
}
