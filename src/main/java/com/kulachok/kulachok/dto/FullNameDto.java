package com.kulachok.kulachok.dto;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FullNameDto {
    @Size(min = 1, max = 50, message = "Nickname must be between 1 and 50 characters")
    private String nickname;

    @NotBlank(message = "Surname cannot be empty")
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String middleName;

    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
}
