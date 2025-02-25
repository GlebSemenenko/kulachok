package com.kulachok.kulachok.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActrisDto {
    private String name;
    private Integer followers;
    private Integer age;
    private String nationality;
}
