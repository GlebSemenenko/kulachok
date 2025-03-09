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

public class StatisticsVideoDto {
    @Min(value = 0, message = "View count cannot be negative")
    private Integer viewCount;

    @Min(value = 0, message = "Like count cannot be negative")
    private Integer likeCount;

    @Min(value = 0, message = "Dislike count cannot be negative")
    private Integer dislikeCount;
}
