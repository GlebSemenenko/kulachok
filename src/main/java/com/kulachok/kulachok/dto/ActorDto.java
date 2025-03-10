package com.kulachok.kulachok.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActorDto {
    @Valid
    @NotNull(message = "Full name cannot be null")
    private FullNameDto nameActor;

    @NotNull(message = "Followers count cannot be null")
    @Min(value = 0, message = "Followers count cannot be negative")
    private Integer followers;

    @NotNull(message = "Age cannot be null")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be less than or equal to 100")
    private Integer age;

    @NotBlank(message = "Nationality cannot be empty")
    @Size(max = 30, message = "Nationality must be up to 30 characters")
    private String nationality;
}
