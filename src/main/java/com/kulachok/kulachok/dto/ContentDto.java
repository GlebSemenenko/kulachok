package com.kulachok.kulachok.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentDto {
    @Min(value = 5, message = "Duration must be at least 1 second")
    private Integer duration;

    @NotBlank(message = "URL cannot be empty")
    private String url;

    @NotBlank(message = "Thumbnail URL cannot be empty")
    private String thumbnailUrl;

    @NotBlank(message = "Format cannot be empty")
    private String format;
}
