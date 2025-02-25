package com.kulachok.kulachok.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CashDto {
    @NotNull(message = "Amount cannot be null")
    @PositiveOrZero(message = "Amount must be zero or positive")
    private BigDecimal amount;
}
