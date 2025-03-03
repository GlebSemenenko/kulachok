package com.kulachok.kulachok.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    @NotNull(message = "User ID cannot be null")
    private Integer idUser;

    @NotNull(message = "Actris ID cannot be null")
    private Integer idActris;
}
