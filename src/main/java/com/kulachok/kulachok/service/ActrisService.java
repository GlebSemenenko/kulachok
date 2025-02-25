package com.kulachok.kulachok.service;

import com.kulachok.kulachok.dto.ActrisDto;
import com.kulachok.kulachok.entity.Actris;

public interface ActrisService {
    Actris update(int id, ActrisDto updatedActris);
    Actris add(ActrisDto actris, int id);
}
