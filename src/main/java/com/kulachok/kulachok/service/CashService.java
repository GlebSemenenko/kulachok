package com.kulachok.kulachok.service;

import com.kulachok.kulachok.dto.CashDto;
import com.kulachok.kulachok.entity.Cash;


public interface CashService {
    Cash recordWalletValue(int userId, CashDto accountCash, String accountType);
}
