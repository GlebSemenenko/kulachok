package com.kulachok.kulachok.service;

import com.kulachok.kulachok.entity.Actris;
import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.Transfer;
import com.kulachok.kulachok.repository.ActrisRepository;
import com.kulachok.kulachok.repository.CashRepository;
import com.kulachok.kulachok.repository.TransferRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ActrisServiceImpl implements ActrisService {

    private final ActrisRepository actrisRepository;

    private final TransferRepository transferRepository;

    private final CashRepository cashRepository;

    public ActrisServiceImpl(ActrisRepository actrisRepository
            , TransferRepository transferRepository
            , CashRepository cashRepository) {
        this.actrisRepository = actrisRepository;
        this.transferRepository = transferRepository;
        this.cashRepository = cashRepository;
    }

    @Override
    public Actris add(Actris actris) {
        Actris savedActris = actrisRepository.save(actris);

        Cash savedCash = new Cash();
        savedCash.setAmount(BigDecimal.ZERO);
        savedCash.setDescription("Оплата за услуги");
        savedCash.setTransferType("DEBIT");
        savedCash.setTransferDate(LocalDateTime.now());
        cashRepository.save(savedCash);

        Transfer savedTransfer = new Transfer();
        savedTransfer.setDescription("При создании");
        savedTransfer.setActris(savedActris);
        savedTransfer.setCashAccount(savedCash);
        transferRepository.save(savedTransfer);
        return savedActris;
    }

    @Override
    public Actris update(int id, Actris updatedActris) {
        Actris existingActris = actrisRepository.findById(id).get();

        existingActris.setName(updatedActris.getName());
        existingActris.setAge(updatedActris.getAge());
        existingActris.setFollowers(updatedActris.getFollowers());
        existingActris.setNationality(updatedActris.getNationality());

        return actrisRepository.save(existingActris);
    }

}


