package com.kulachok.kulachok.service;

import com.kulachok.kulachok.entity.Actris;
import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.CashAccountHolder;
import com.kulachok.kulachok.entity.Transfer;
import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.repository.ActrisRepository;
import com.kulachok.kulachok.repository.CashRepository;
import com.kulachok.kulachok.repository.TransferRepository;
import com.kulachok.kulachok.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
public class CashServiceImpl implements CashService {

    private final UserRepository userRepository;

    private final CashRepository cashRepository;

    private final TransferRepository transferRepository;

    private final ActrisRepository actrisRepository;

    public CashServiceImpl(UserRepository userRepository, CashRepository cashRepository, TransferRepository transferRepository, ActrisRepository actrisRepository) {
        this.userRepository = userRepository;
        this.cashRepository = cashRepository;
        this.transferRepository = transferRepository;
        this.actrisRepository = actrisRepository;
    }

    @Override
    public Cash updateCash(int id, Cash userCash, Class<? extends CashAccountHolder> userType) {
        CashAccountHolder existingUser;

        Cash cash;

        if (User.class.isAssignableFrom(userType)) {
            existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            cash = cashRepository.findByUser((User) existingUser);
        } else if (Actris.class.isAssignableFrom(userType)) {
            existingUser = actrisRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Actress not found"));
            cash = cashRepository.findByActris((Actris) existingUser);
        } else {
            throw new RuntimeException("Unknown user type");
        }

        if (cash == null) {
            throw new RuntimeException("Cash account not found");
        }

        // Обновление баланса
        BigDecimal amountCash = cash.getAmount();
        BigDecimal amountUser = userCash.getAmount();

        cash.setAmount(amountCash.add(amountUser));
        cash = cashRepository.save(cash);


        Transfer transfer = new Transfer();
        transfer.setDescription(
                amountUser.compareTo(BigDecimal.ZERO) < 0
                        ? "Уменьшение баланса"
                        : amountUser.compareTo(BigDecimal.ZERO) > 0
                        ? "Увеличение баланса"
                        : "Баланс не изменился"
        );
        transfer.setSumTransfer(amountUser);
        transfer.setAllSumTransfer(cash.getAmount());

        if (existingUser instanceof User) {
            transfer.setUser((User) existingUser);
        } else {
            transfer.setActris((Actris) existingUser);
        }

        transfer.setCashAccount(cash);
        transferRepository.save(transfer);

        return cash;
    }


}
