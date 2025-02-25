package com.kulachok.kulachok.service;

import com.kulachok.kulachok.dto.CashDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class CashServiceImpl implements CashService {
    private final UserRepository userRepository;
    private final CashRepository cashRepository;
    private final ActrisRepository actrisRepository;
    private final TransferRepository transferRepository;

    public CashServiceImpl(UserRepository userRepository
            , CashRepository cashRepository
            , ActrisRepository actrisRepository
            , TransferRepository transferRepository) {
        this.userRepository = userRepository;
        this.cashRepository = cashRepository;
        this.actrisRepository = actrisRepository;
        this.transferRepository = transferRepository;
    }

    @Transactional
    @Override
    public Cash updateCash(int id, CashDto accountCash, Class<? extends CashAccountHolder> accountType) {
        CashAccountHolder accountHolder = getAccountHolder(id, accountType);
        Cash cash = getCashAccount(accountHolder);
        BigDecimal newBalance = getBigDecimal(accountCash, accountHolder, cash);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient funds: balance cannot be negative");
        }


        cash.setAmount(newBalance);
        cash = cashRepository.save(cash);
        log.info("Cash account updated");

        saveTransfer(accountCash, newBalance, accountHolder, cash);

        return cash;
    }

    private static BigDecimal getBigDecimal(CashDto accountCash, CashAccountHolder accountHolder, Cash cash) {
        BigDecimal newBalance;

        if (accountHolder instanceof User) {
            newBalance = cash.getAmount().add(accountCash.getAmount());
        } else if (accountHolder instanceof Actris) {
            newBalance = cash.getAmount().subtract(accountCash.getAmount());
        } else {
            throw new RuntimeException("Unknown user type");
        }
        return newBalance;
    }

    private CashAccountHolder getAccountHolder(int id, Class<? extends CashAccountHolder> accountType) {
        if (User.class.isAssignableFrom(accountType)) {
            return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        } else if (Actris.class.isAssignableFrom(accountType)) {
            return actrisRepository.findById(id).orElseThrow(() -> new RuntimeException("Actress not found"));
        }
        throw new RuntimeException("Unknown account type");
    }

    private Cash getCashAccount(CashAccountHolder accountHolder) {
        List<Cash> cashList;

        if (accountHolder instanceof User) {
            cashList = cashRepository.findByUser((User) accountHolder);
        } else if (accountHolder instanceof Actris) {
            cashList = cashRepository.findByActris((Actris) accountHolder);
        } else {
            throw new RuntimeException("Unknown account holder type");
        }

        return cashList.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cash account not found"));
    }

    private void saveTransfer(CashDto accountCash, BigDecimal newBalance, CashAccountHolder accountHolder, Cash cash) {
        Transfer transfer = new Transfer();
        transfer.setDescription(accountCash.getAmount().compareTo(BigDecimal.ZERO) < 0
                ? "Balance decreased" : "Balance increased");
        transfer.setSumTransfer(accountCash.getAmount());
        transfer.setAllSumTransfer(newBalance);
        transfer.setCashAccount(cash);

        if (accountHolder instanceof User) {
            transfer.setUser((User) accountHolder);
        } else {
            transfer.setActris((Actris) accountHolder);
        }

        transferRepository.save(transfer);
    }

}