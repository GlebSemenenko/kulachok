package com.kulachok.kulachok.dto;

import jakarta.validation.Valid;
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

    @Size(max = 20, message = "Maximum number of tags is 20")
    private List<@Size(max = 20, message = "Maximum number of tags is 20")String> tags;

    @Valid
    private IdentificationDto identification;

    @Valid
    private StatisticsVideoDto statisticsVideo;

    @Valid
    private ContentDto content;
    private Integer actorId;

}
