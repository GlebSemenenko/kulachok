package com.kulachok.kulachok.dto;

import jakarta.validation.constraints.Email;
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
public class UserDto {
    @Size(min = 1, max = 30, message = "Username must be between 3 and 30 characters")
    private String username;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 120, message = "Age must be less than or equal to 120")
    private Integer age;

    @Email(message = "Email should be valid")
    private String email;

    @Size(min = 6, message = "Password must be at least 8 characters long")
    private String password;
}