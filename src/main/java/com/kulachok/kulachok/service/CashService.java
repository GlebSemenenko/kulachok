package com.kulachok.kulachok.service;

import com.kulachok.kulachok.dto.CashDto;
import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.model_Interface.CashAccountHolder;

public interface CashService {


    Cash updateCash(int userId, CashDto accountCash, Class<? extends CashAccountHolder> accountType);
}
