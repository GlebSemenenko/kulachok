package com.kulachok.kulachok.service;

import com.kulachok.kulachok.dto.UserDto;
import com.kulachok.kulachok.entity.Actris;
import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.Transfer;
import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.entity.UserSubscription;
import com.kulachok.kulachok.entity.model_Interface.CashAccountHolder;
import exception.ResourceNotFoundException;
import com.kulachok.kulachok.repository.ActrisRepository;
import com.kulachok.kulachok.repository.CashRepository;
import com.kulachok.kulachok.repository.TransferRepository;
import com.kulachok.kulachok.repository.UserRepository;
import com.kulachok.kulachok.repository.UserSubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.PasswordUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CashRepository cashRepository;

    private final TransferRepository transferRepository;

    private final ActrisRepository actrisRepository;

    private final UserSubscriptionRepository usrSubscriptionRepository;

    public UserServiceImpl(UserRepository userRepository
            , CashRepository cashRepository
            , TransferRepository transferRepository
            , ActrisRepository actrisRepository
            , UserSubscriptionRepository usrSubscriptionRepository) {
        this.userRepository = userRepository;
        this.cashRepository = cashRepository;
        this.transferRepository = transferRepository;
        this.actrisRepository = actrisRepository;
        this.usrSubscriptionRepository = usrSubscriptionRepository;
    }

    @Override
    public User add(UserDto userDto) {

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(PasswordUtil.hashPassword(userDto.getPassword().toCharArray()));

        User savedUser = userRepository.save(user);

        Cash savedCash = new Cash();
        savedCash.setUser(savedUser);
        savedCash.setAmount(BigDecimal.ZERO);
        savedCash.setDescription("Оплата за услуги");
        savedCash.setTransferType("DEBIT");
        savedCash.setTransferDate(LocalDateTime.now());
        cashRepository.save(savedCash);

        Transfer savedTransfer = new Transfer();
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
                .orElseThrow(() -> new RuntimeException("Cash account not found"));
        Actris actris = actrisRepository.findById(cash.getActris().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Actress not found"));

        deleteTransfers(actris);
        deleteTransfers(user);
        deleteUserSubscriptions(user);
        actrisRepository.delete(actris);
        cashRepository.delete(cash);
        userRepository.delete(user);
    }

    private void deleteUserSubscriptions(User user) {
        List<UserSubscription> subscriptions = user.getSubscriptions();
        usrSubscriptionRepository.deleteAll(subscriptions);
    }


    private void deleteTransfers(CashAccountHolder accountHolder) {
        List<Transfer> transfers;

        if (accountHolder == null) {
            throw new NullPointerException("Account holder is null");
        }

        if (User.class.isAssignableFrom(accountHolder.getClass())) {
            transfers = transferRepository.findByUser((User) accountHolder);
        } else if (Actris.class.isAssignableFrom(accountHolder.getClass())) {
            transfers = transferRepository.findByActris((Actris) accountHolder);
        } else {
            throw new IllegalArgumentException("Unknown account holder type");
        }

        if (transfers.isEmpty()) {
            throw new NoSuchElementException("Transfers not found");
        }

        transferRepository.deleteAll(transfers);
    }

}

