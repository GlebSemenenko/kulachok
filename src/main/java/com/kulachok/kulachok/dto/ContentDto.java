package com.kulachok.kulachok.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentDto {
    @Min(value = 5, message = "Duration must be at least 5 second")
    private Integer duration;

    private String url;

    private String thumbnailUrl;

    private String format;
}
