package com.kulachok.kulachok.entity.full_request;

import com.kulachok.kulachok.dto.ActorDto;
import com.kulachok.kulachok.dto.FullNameDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActorRequest {
    private ActorDto actor;
    private FullNameDto flmn;
}
