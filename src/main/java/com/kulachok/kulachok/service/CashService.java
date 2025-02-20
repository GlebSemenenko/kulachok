package com.kulachok.kulachok.service;

import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.CashAccountHolder;
import org.springframework.transaction.annotation.Transactional;

public interface CashService {

    @Transactional
    Cash updateCash(int userId, Cash userCash, Class<? extends CashAccountHolder> userType);
}
