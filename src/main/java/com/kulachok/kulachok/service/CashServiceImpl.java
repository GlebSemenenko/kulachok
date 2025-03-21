package com.kulachok.kulachok.service;

import com.kulachok.kulachok.dto.CashDto;
import com.kulachok.kulachok.entity.Actor;
import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.Transfer;
import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.entity.model_interface.CashAccountHolder;
import com.kulachok.kulachok.repository.ActorRepository;
import com.kulachok.kulachok.repository.CashRepository;
import com.kulachok.kulachok.repository.TransferRepository;
import com.kulachok.kulachok.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.UtilBalance;
import util.UtilEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
public class CashServiceImpl implements CashService {
    private final UserRepository userRepository;
    private final CashRepository cashRepository;
    private final ActorRepository actorRepository;
    private final TransferRepository transferRepository;

    @Autowired
    public CashServiceImpl(UserRepository userRepository
            , CashRepository cashRepository
            , ActorRepository actorRepository
            , TransferRepository transferRepository) {
        this.userRepository = userRepository;
        this.cashRepository = cashRepository;
        this.actorRepository = actorRepository;
        this.transferRepository = transferRepository;
    }


    public Cash recordWalletValue(int userId, CashDto accountCash, String accountType) {
        Class<? extends CashAccountHolder> userType = UtilEntity.getUserTypeAndCheckExistence(userId,
                accountType,
                userRepository,
                actorRepository);
        CashAccountHolder accountHolder = getAccountHolder(userId, userType);
        Cash cash = getCashAccount(accountHolder);
        BigDecimal newBalance = getBigDecimal(accountCash, accountHolder, cash);

        UtilBalance.checkBalanceNonNegative(newBalance);

        cash.setAmount(newBalance);
        cash = cashRepository.save(cash);
        log.info("Cash account updated for {} with ID {} with new balance: {}", accountType, userId, newBalance);

        saveTransfer(accountCash, newBalance, accountHolder, cash);
        return cash;
    }

    private BigDecimal getBigDecimal(CashDto accountCash, CashAccountHolder accountHolder, Cash cash) {
        BigDecimal newBalance;

        if (accountHolder instanceof User) {
            newBalance = cash.getAmount().add(accountCash.getAmount());
        } else if (accountHolder instanceof Actor) {
            newBalance = cash.getAmount().subtract(accountCash.getAmount());
        } else {
            throw new IllegalArgumentException("Unknown user type");
        }

        return newBalance;
    }

    private CashAccountHolder getAccountHolder(int id, Class<? extends CashAccountHolder> accountType) {
        if (User.class.isAssignableFrom(accountType)) {
            return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        } else if (Actor.class.isAssignableFrom(accountType)) {
            return actorRepository.findById(id).orElseThrow(() -> new RuntimeException("Actress not found"));
        }
        throw new IllegalArgumentException("Unknown account type");
    }

    private Cash getCashAccount(CashAccountHolder accountHolder) {
        return (accountHolder instanceof User user) ?
                cashRepository.findByUser(user)
                        .orElseThrow(() -> new RuntimeException("Cash account not found")) :
                cashRepository.findByActor((Actor) accountHolder)
                        .orElseThrow(() -> new RuntimeException("Cash account not found"));
    }

    private void saveTransfer(CashDto accountCash, BigDecimal newBalance, CashAccountHolder accountHolder, Cash cash) {
        Transfer transfer = new Transfer();
        transfer.setDescription(accountCash.getAmount().compareTo(BigDecimal.ZERO) < 0
                ? "Balance decreased" : "Balance increased");
        transfer.setSumTransfer(accountCash.getAmount());
        transfer.setAllSumTransfer(newBalance);
        transfer.setCashAccount(cash);
        transfer.setTransferDate(LocalDateTime.now());

        if (accountHolder instanceof User user) {
            transfer.setUser(user);
            transfer.setTransferType("CREDIT");
        } else if (accountHolder instanceof Actor actor) {
            transfer.setActor(actor);
            transfer.setTransferType("DEBIT");
        } else {
            throw new IllegalArgumentException("Unknown account holder type: " + accountHolder.getClass().getName());
        }

        transferRepository.save(transfer);
    }
}