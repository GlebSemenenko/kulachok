package com.kulachok.kulachok.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
    private FullNameDto nameActor;
    
    @Min(value = 0, message = "Followers count cannot be negative")
    private Integer followers;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 120, message = "Age must be less than or equal to 120")
    private Integer age;
    
    @Size(max = 30, message = "Nationality must be up to 30 characters")
    private String nationality;
}
