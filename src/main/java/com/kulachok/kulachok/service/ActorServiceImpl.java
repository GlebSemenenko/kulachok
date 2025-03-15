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

import static util.UtilEntity.checkFieldForNull;
import static util.UtilEntity.checkFieldForNullException;
import static util.UtilEntity.checkFieldForNullOrEmpty;
import static util.UtilEntity.checkFieldForNullOrEmptyException;

@Service
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;
    private final TransferRepository transferRepository;
    private final CashRepository cashRepository;
    private final UserRepository userRepository;

    @Autowired
    public ActorServiceImpl(ActorRepository actorRepository,
                            TransferRepository transferRepository,
                            CashRepository cashRepository,
                            UserRepository userRepository) {
        this.actorRepository = actorRepository;
        this.transferRepository = transferRepository;
        this.cashRepository = cashRepository;
        this.userRepository = userRepository;

    }

    @Transactional
    public Actor add(int userId, ActorDto actorDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getActor() != null) {
            throw new IllegalStateException("Актриса уже создана для данного пользователя");
        }

        Actor actor = new Actor();
        mappedDataActor(actorDto, actor);
        actor.setUser(user);

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

        mappedDataActor(actor, existingActor);

        return actorRepository.save(existingActor);
    }

    private void mappedDataActor(ActorDto actor, Actor existingActor) {
        FullName nameActor = new FullName();
        FullName existingName = existingActor.getNameActor();

        if (actor.getNameActor() != null) {
            checkFieldForNullOrEmpty(actor.getNameActor().getNickname(), "Nickname", nameActor::setNickname);
            checkFieldForNullOrEmptyException(actor.getNameActor().getFirstName(), "FirstName", nameActor::setFirstName);
            checkFieldForNullOrEmptyException(actor.getNameActor().getMiddleName(), "MiddleName", nameActor::setMiddleName);
            checkFieldForNullOrEmpty(actor.getNameActor().getLastName(), "LastName", nameActor::setLastName);
        } else {
            nameActor = existingName;
        }
        existingActor.setNameActor(nameActor);

        checkFieldForNullException(actor.getAge(), "Age", existingActor::setAge);
        checkFieldForNull(actor.getFollowers(), "Followers", existingActor::setFollowers);
        checkFieldForNullOrEmpty(actor.getNationality(), "Nationality", existingActor::setNationality);

        existingActor.setRegistrationDate(LocalDate.now());
    }
}


