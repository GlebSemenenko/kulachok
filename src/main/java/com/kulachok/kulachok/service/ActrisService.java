package com.kulachok.kulachok.service;

import com.kulachok.kulachok.entity.Actris;
import com.kulachok.kulachok.entity.User;

public interface ActrisService {
    Actris update(int id, Actris updatedActris);
    Actris add(Actris actris, int id);
}
