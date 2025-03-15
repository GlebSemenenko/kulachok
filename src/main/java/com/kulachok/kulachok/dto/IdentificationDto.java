package com.kulachok.kulachok.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdentificationDto {
    private String title;

    @Size(min = 1, max = 1000, message = "Description must be between 1 and 1000 characters")
    private String description;

    private String status;

    private String category;
}