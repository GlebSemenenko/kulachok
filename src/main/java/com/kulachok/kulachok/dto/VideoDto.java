package com.kulachok.kulachok.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {

    @NotEmpty(message = "Tags cannot be empty")
    @Size(max = 20, message = "Maximum number of tags is 20")
    private List<@NotBlank(message = "Tag cannot be empty") @Size(max = 20, message = "Maximum number of tags is 20")String> tags;

    @Valid
    private IdentificationDto identification;

    @Valid
    private StatisticsVideoDto statisticsVideo;

    @Valid
    private ContentDto content;
    private Integer actorId;
}
