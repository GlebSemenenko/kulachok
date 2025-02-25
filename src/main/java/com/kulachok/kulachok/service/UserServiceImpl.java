package com.kulachok.kulachok.service;

import com.kulachok.kulachok.dto.UserDto;
import com.kulachok.kulachok.entity.Actris;
import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.CashAccountHolder;
import com.kulachok.kulachok.entity.Transfer;
import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.entity.UserSubscription;
import com.kulachok.kulachok.repository.ActrisRepository;
import com.kulachok.kulachok.repository.CashRepository;
import com.kulachok.kulachok.repository.TransferRepository;
import com.kulachok.kulachok.repository.UserRepository;
import com.kulachok.kulachok.repository.UserSubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
        savedTransfer.setDescription("При создании");
        transferRepository.save(savedTransfer);
        return savedUser;
    }

    @Override
    public User update(int id, UserDto userDto) {
        User user = userRepository.findById(id).get();

        user.setUsername(userDto.getUsername());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());

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


    private void deleteTransfers(Actris accountHolder) {
        List<Transfer> transfers = transferRepository.findByActris(accountHolder);
        transferRepository.deleteAll(transfers);
    }

    private void deleteTransfers(User userHolder) {
        List<Transfer> transfers = transferRepository.findByUser(userHolder);
        transferRepository.deleteAll(transfers);
    }

}

