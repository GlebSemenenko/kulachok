package com.kulachok.kulachok.service;

import com.kulachok.kulachok.dto.ActorDto;
import com.kulachok.kulachok.dto.FullName;
import com.kulachok.kulachok.entity.Actor;

public interface ActorService {
    Actor update(int id, ActorDto actorDto, FullName fullName);
    Actor add(int id, ActorDto actorDto, FullName fullName);
}
