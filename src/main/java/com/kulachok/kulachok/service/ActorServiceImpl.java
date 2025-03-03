package com.kulachok.kulachok.service;

import com.kulachok.kulachok.dto.ActorDto;
import com.kulachok.kulachok.entity.Actor;
import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.FullName;
import com.kulachok.kulachok.entity.Transfer;
import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.repository.ActorRepository;
import com.kulachok.kulachok.repository.CashRepository;
import com.kulachok.kulachok.repository.TransferRepository;
import com.kulachok.kulachok.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;

    private final TransferRepository transferRepository;

    private final CashRepository cashRepository;

    private final UserRepository userRepository;


    public ActorServiceImpl(ActorRepository actorRepository
            , TransferRepository transferRepository
            , CashRepository cashRepository
            , UserRepository userRepository) {
        this.actorRepository = actorRepository;
        this.transferRepository = transferRepository;
        this.cashRepository = cashRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Actor add(int userId, ActorDto actorDto, com.kulachok.kulachok.dto.FullName fullName) {
        // Проверяем, существует ли пользователь
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Создаем нового актера
        Actor actor = new Actor();
        FullName name = new FullName();
        name.setNickname(fullName.getNickname());
        name.setFirstName(fullName.getFirstName());
        name.setMiddleName(fullName.getMiddleName());
        name.setLastName(fullName.getLastName());
        actor.setNameActor(name);
        actor.setAge(actorDto.getAge());
        actor.setFollowers(actorDto.getFollowers());
        actor.setNationality(actorDto.getNationality());
        actor.setRegistrationDate(LocalDate.now());

        // Сохраняем актера
        Actor savedActor = actorRepository.save(actor);

        // Получаем кассу пользователя
        Cash cash = cashRepository.findByUser(user)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cash account not found"));

        // Устанавливаем связь между кассой и актером
        cash.setActor(savedActor);
        user.setActor(savedActor);
        cashRepository.save(cash);

        // Создаем перевод
        Transfer savedTransfer = createTransfer(savedActor, cash);
        transferRepository.save(savedTransfer);

        return savedActor;
    }

    private Transfer createTransfer(Actor actor, Cash cash) {
        Transfer transfer = new Transfer();
        transfer.setActor(actor);
        transfer.setCashAccount(cash);
        transfer.setDescription("При создании");
        transfer.setTransferDate(LocalDateTime.now());
        return transfer;
    }


    @Override
    public Actor update(int id, ActorDto actor, com.kulachok.kulachok.dto.FullName fullName) {
        Actor existingActor = actorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " not found"));

        FullName nameActor = new FullName();
        nameActor.setNickname(fullName.getNickname());
        nameActor.setFirstName(fullName.getFirstName());
        nameActor.setLastName(fullName.getLastName());
        nameActor.setMiddleName(fullName.getMiddleName());

        existingActor.setNameActor(nameActor);
        existingActor.setAge(actor.getAge());
        existingActor.setFollowers(actor.getFollowers());
        existingActor.setNationality(actor.getNationality());
        existingActor.setRegistrationDate(LocalDate.now());

        return actorRepository.save(existingActor);
    }

}


