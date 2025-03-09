package com.kulachok.kulachok.service;

import com.kulachok.kulachok.dto.UserDto;
import com.kulachok.kulachok.entity.Actor;
import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.Transfer;
import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.entity.Subscription;
import com.kulachok.kulachok.entity.Video;
import com.kulachok.kulachok.entity.model_interface.CashAccountHolder;
import com.kulachok.kulachok.repository.VideoRepository;
import exception.ResourceNotFoundException;
import com.kulachok.kulachok.repository.ActorRepository;
import com.kulachok.kulachok.repository.CashRepository;
import com.kulachok.kulachok.repository.TransferRepository;
import com.kulachok.kulachok.repository.UserRepository;
import com.kulachok.kulachok.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.PasswordUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    //todo добавить логирование при сиключения

    private final UserRepository userRepository;
    private final CashRepository cashRepository;
    private final TransferRepository transferRepository;
    private final ActorRepository actorRepository;
    private final SubscriptionRepository usrSubscriptionRepository;
    private final VideoRepository videoRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository
            , CashRepository cashRepository
            , TransferRepository transferRepository
            , ActorRepository actorRepository
            , SubscriptionRepository usrSubscriptionRepository
            , VideoRepository videoRepository) {
        this.userRepository = userRepository;
        this.cashRepository = cashRepository;
        this.transferRepository = transferRepository;
        this.actorRepository = actorRepository;
        this.usrSubscriptionRepository = usrSubscriptionRepository;
        this.videoRepository = videoRepository;
    }

    @Override
    public User add(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(PasswordUtil.hashPassword(userDto.getPassword().toCharArray()));
        user.setRegistrationDate(LocalDate.now());

        User savedUser = userRepository.save(user);

        Cash savedCash = new Cash();
        savedCash.setUser(savedUser);
        savedCash.setAmount(BigDecimal.ZERO);
        savedCash.setDescription("Оплата за услуги");
        savedCash.setTransferDate(LocalDateTime.now());

        cashRepository.save(savedCash);

        Transfer savedTransfer = new Transfer();
        savedTransfer.setCashAccount(savedCash);
        savedTransfer.setUser(savedUser);
        savedTransfer.setTransferDate(LocalDateTime.now());
        savedTransfer.setDescription("При создании");

        transferRepository.save(savedTransfer);

        return savedUser;
    }

    @Override
    public User update(int id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id "
                        + id + " not found"));

        user.setUsername(userDto.getUsername());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(int userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cash cash = cashRepository.findByUser(user)
                .stream()
                .findFirst()
                .orElse(null);

        deleteTransfers(user);
        deleteUserSubscriptions(user);

        if (cash != null) {
            Actor actor = cash.getActor();

            if (actor != null) {
                deleteVideo(actor.getId());
                deleteTransfers(actor);
                actorRepository.delete(actor);
            }
            cashRepository.delete(cash);
        }

        userRepository.delete(user);
    }

    private void deleteUserSubscriptions(User user) {
        List<Subscription> subscriptions = user.getSubscriptions();
        usrSubscriptionRepository.deleteAll(subscriptions);
    }

    private void deleteVideo(Integer actorId) {
        List<Video> videos = videoRepository.findByAuthorId(actorId);

        if (videos.isEmpty()) {
            throw new NoSuchElementException("No videos found for actor with id: " + actorId);
        }

        videoRepository.deleteAll(videos);
    }


    private void deleteTransfers(CashAccountHolder accountHolder) {
        List<Transfer> transfers;

        if (accountHolder == null) {
            throw new NullPointerException("Account holder is null");
        }

        if (User.class.isAssignableFrom(accountHolder.getClass())) {
            transfers = transferRepository.findByUser((User) accountHolder);
        } else if (Actor.class.isAssignableFrom(accountHolder.getClass())) {
            transfers = transferRepository.findByActor((Actor) accountHolder);
        } else {
            throw new IllegalArgumentException("Unknown account holder type");
        }

        if (transfers.isEmpty()) {
            throw new NoSuchElementException("Transfers not found");
        }

        transferRepository.deleteAll(transfers);
    }
}

