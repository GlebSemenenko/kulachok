package com.kulachok.kulachok.service;

import com.kulachok.kulachok.entity.Actris;
import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.Transfer;
import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.repository.ActrisRepository;
import com.kulachok.kulachok.repository.CashRepository;
import com.kulachok.kulachok.repository.TransferRepository;
import com.kulachok.kulachok.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActrisServiceImpl implements ActrisService{

    private final ActrisRepository actrisRepository;

    private final TransferRepository transferRepository;

    private final CashRepository cashRepository;

    private final UserRepository userRepository;


    public ActrisServiceImpl(ActrisRepository actrisRepository
            , TransferRepository transferRepository
            , CashRepository cashRepository
            , UserRepository userRepository) {
        this.actrisRepository = actrisRepository;
        this.transferRepository = transferRepository;
        this.cashRepository = cashRepository;
        this.userRepository = userRepository;
    }

    public Actris add(Actris actris, int userId) {
        // Сохраняем новую актрису
        Actris savedActris = actrisRepository.save(actris);

        // Находим пользователя по userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Находим кошелек пользователя
        Cash cash = cashRepository.findByUser(user)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cash account not found"));

        // Обновляем кошелек, добавляя ID созданной актрисы
        cash.setActris(savedActris);
        cashRepository.save(cash);

        // Создаём и сохраняем перевод
        Transfer savedTransfer = createTransfer(savedActris, cash);
        transferRepository.save(savedTransfer);

        return savedActris;
    }

    private Transfer createTransfer(Actris actris, Cash cash) {
        Transfer transfer = new Transfer();
        transfer.setActris(actris);
        transfer.setCashAccount(cash);
        transfer.setDescription("При создании");
        return transfer;
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