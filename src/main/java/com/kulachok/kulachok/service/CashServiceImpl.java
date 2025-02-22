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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class CashServiceImpl implements CashService {
    private final UserRepository userRepository;
    private final CashRepository cashRepository;
    private final TransferRepository transferRepository;
    private final ActrisRepository actrisRepository;

    public CashServiceImpl(UserRepository userRepository
            , CashRepository cashRepository
            , TransferRepository transferRepository
            , ActrisRepository actrisRepository) {
        this.userRepository = userRepository;
        this.cashRepository = cashRepository;
        this.transferRepository = transferRepository;
        this.actrisRepository = actrisRepository;
    }

    @Override
    public Cash updateCash(int id, Cash userCash, Class<? extends CashAccountHolder> userType) {
        CashAccountHolder existingUser = getExistingUser(id, userType);
        Cash cash = getCashAccount(existingUser, userType);
        BigDecimal newBalance = getBigDecimal(userCash, existingUser, cash);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient funds: balance cannot be negative");
        }


        cash.setAmount(newBalance);
        cash = cashRepository.save(cash);
        log.info("Cash account updated");

        saveTransfer(userCash, newBalance, existingUser, cash);

        return cash;
    }

    private static BigDecimal getBigDecimal(Cash userCash, CashAccountHolder existingUser, Cash cash) {
        BigDecimal newBalance;

        if (existingUser instanceof User) {
            newBalance = cash.getAmount().add(userCash.getAmount());
        } else if (existingUser instanceof Actris) {
            newBalance = cash.getAmount().subtract(userCash.getAmount());
        } else {
            throw new RuntimeException("Unknown user type");
        }
        return newBalance;
    }

    private CashAccountHolder getExistingUser(int id, Class<? extends CashAccountHolder> userType) {
        if (User.class.isAssignableFrom(userType)) {
            return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        } else if (Actris.class.isAssignableFrom(userType)) {
            return actrisRepository.findById(id).orElseThrow(() -> new RuntimeException("Actress not found"));
        } else {
            throw new RuntimeException("Unknown user type");
        }
    }

    private Cash getCashAccount(CashAccountHolder existingUser, Class<? extends CashAccountHolder> userType) {
        List<Cash> cashList;

        if (User.class.isAssignableFrom(userType)) {
            cashList = cashRepository.findByUser((User) existingUser);
        } else if (Actris.class.isAssignableFrom(userType)) {
            cashList = cashRepository.findByActris((Actris) existingUser);
        } else {
            throw new RuntimeException("Unknown user type");
        }

        return cashList.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cash account not found"));
    }


    private void saveTransfer(Cash userCash, BigDecimal newBalance, CashAccountHolder existingUser, Cash cash) {
        Transfer transfer = new Transfer();
        transfer.setDescription(userCash.getAmount().compareTo(BigDecimal.ZERO) < 0
                ? "Balance decreased"
                : userCash.getAmount().compareTo(BigDecimal.ZERO) > 0
                ? "Balance increased"
                : "The balance has not changed");
        transfer.setSumTransfer(userCash.getAmount());
        transfer.setAllSumTransfer(newBalance);
        transfer.setCashAccount(cash);

        if (existingUser instanceof User) {
            transfer.setUser((User) existingUser);
        } else {
            transfer.setActris((Actris) existingUser);
        }

        transferRepository.save(transfer);
    }

    public Cash createCash(Actris actris, int cashId) {
        User user = userRepository.findById(cashId).orElseThrow(() -> new RuntimeException("User not found"));
        Cash cash = (Cash) cashRepository.findByUser(user);
        cash.setActris(actris);
        return cashRepository.save(cash);
    }

}