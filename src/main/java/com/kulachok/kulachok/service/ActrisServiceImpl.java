package com.kulachok.kulachok.service;

import com.kulachok.kulachok.dto.ActrisDto;
import com.kulachok.kulachok.entity.Actris;
import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.Transfer;
import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.repository.ActrisRepository;
import com.kulachok.kulachok.repository.CashRepository;
import com.kulachok.kulachok.repository.TransferRepository;
import com.kulachok.kulachok.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

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

    public Actris add(ActrisDto actrisDto, int userId) {
        // Сохраняем новую актрису
        Actris actris = new Actris();
        actris.setName(actrisDto.getName());
        actris.setAge(actrisDto.getAge());
        actris.setFollowers(actrisDto.getFollowers());
        actris.setNationality(actrisDto.getNationality());
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
    public Actris update(int id, ActrisDto actris) {
        Actris existingActris = actrisRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " not found"));

        existingActris.setName(actris.getName());
        existingActris.setAge(actris.getAge());
        existingActris.setFollowers(actris.getFollowers());
        existingActris.setNationality(actris.getNationality());

        return actrisRepository.save(existingActris);
    }

}


