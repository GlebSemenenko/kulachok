package com.kulachok.kulachok.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActorRequestDto {
    private ActorDto actor;
    private FullName flmn;
}
