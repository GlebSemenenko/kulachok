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
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
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
    public Actor add(int userId, ActorDto actorDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Actor actor = new Actor();
        FullName name = new FullName();
        name.setNickname(actorDto.getNameActor().getNickname());
        name.setFirstName(actorDto.getNameActor().getFirstName());
        name.setMiddleName(actorDto.getNameActor().getMiddleName());
        name.setLastName(actorDto.getNameActor().getLastName());
        actor.setNameActor(name);
        actor.setAge(actorDto.getAge());
        actor.setFollowers(actorDto.getFollowers());
        actor.setNationality(actorDto.getNationality());
        actor.setRegistrationDate(LocalDate.now());

        Actor savedActor = actorRepository.save(actor);

        Cash cash = cashRepository.findByUser(user)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cash account not found"));

        cash.setActor(savedActor);
        user.setActor(savedActor);
        cashRepository.save(cash);

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
    public Actor update(int id, ActorDto actor) {
        Actor existingActor = actorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " not found"));

        FullName nameActor = new FullName();
        nameActor.setNickname(actor.getNameActor().getNickname());
        nameActor.setFirstName(actor.getNameActor().getFirstName());
        nameActor.setMiddleName(actor.getNameActor().getMiddleName());
        nameActor.setLastName(actor.getNameActor().getLastName());

        existingActor.setNameActor(nameActor);
        existingActor.setAge(actor.getAge());
        existingActor.setFollowers(actor.getFollowers());
        existingActor.setNationality(actor.getNationality());
        existingActor.setRegistrationDate(LocalDate.now());

        return actorRepository.save(existingActor);
    }

}


