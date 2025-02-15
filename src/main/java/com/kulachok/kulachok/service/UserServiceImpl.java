package com.kulachok.kulachok.service;

import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.Transfer;
import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.repository.CashRepository;
import com.kulachok.kulachok.repository.TransferRepository;
import com.kulachok.kulachok.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CashRepository cashRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Override
    public User add(User user) {

        User savedUser = userRepository.save(user);

        Cash savedCash = new Cash();
        savedCash.setUser(savedUser);
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

    @Override
    public Cash updateCash(int userId, Cash user) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cash cash = cashRepository.findByUser(existingUser);
        if (cash == null) {
            throw new RuntimeException("Cash account not found");
        }

        // Добавляем сумму
        cash.setAmount(cash.getAmount().add(user.getAmount())); // Добавляем переданную сумму

        // Сохраняем обновленный Cash
        return cashRepository.save(cash);
    }
}
