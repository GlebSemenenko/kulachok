package com.kulachok.kulachok.service;

import com.kulachok.kulachok.entity.Actris;
import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.CashAccountHolder;
import com.kulachok.kulachok.entity.Transfer;
import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.repository.ActrisRepository;
import com.kulachok.kulachok.repository.CashRepository;
import com.kulachok.kulachok.repository.TransferRepository;
import com.kulachok.kulachok.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CashRepository cashRepository;

    private final TransferRepository transferRepository;

    public UserServiceImpl(UserRepository userRepository, CashRepository cashRepository, TransferRepository transferRepository) {
        this.userRepository = userRepository;
        this.cashRepository = cashRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    public User add(User user) {

        User savedUser = userRepository.save(user);

        Cash savedCash = new Cash();
        savedCash.setAmount(BigDecimal.ZERO);
        savedCash.setDescription("Оплата за услуги");
        savedCash.setTransferType("DEBIT");
        savedCash.setTransferDate(LocalDateTime.now());
        cashRepository.save(savedCash);

        // Создаем новую транзакцию, связанную с сохраненным пользователем и кошельком
        Transfer savedTransfer = new Transfer();
        savedTransfer.setDescription("При создании");
        savedTransfer.setUser(savedUser);
        savedTransfer.setCashAccount(savedCash);
        transferRepository.save(savedTransfer);
        return savedUser;
    }

    @Override
    public User update(int id, User updatedUser) {
        User existingUser = userRepository.findById(id).get();

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setEmail(updatedUser.getEmail());

        return userRepository.save(existingUser);
    }
}
