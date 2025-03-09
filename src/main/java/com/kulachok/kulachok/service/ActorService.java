package com.kulachok.kulachok.service;

import com.kulachok.kulachok.dto.ActorDto;
import com.kulachok.kulachok.entity.Actor;

public interface ActorService {
    Actor update(int id, ActorDto actorDto);
    Actor add(int id, ActorDto actorDto);
}
