package com.kulachok.kulachok.service;

import com.kulachok.kulachok.entity.Actris;

public interface ActrisService {
    Actris update(int id, Actris updatedActris);
    Actris add(Actris actris);
}
