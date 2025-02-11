package com.kulachok.kulachok.service;

import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.Transaction;
import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.repository.CaseRepository;
import com.kulachok.kulachok.repository.TransactionRepository;
import com.kulachok.kulachok.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public User addUser(User user) {

        User savedUser = userRepository.save(user);

        Cash savedCash = new Cash();
        savedCash.setUser(savedUser);
        savedCash.setAmount(BigDecimal.ZERO);
        savedCash.setDescription("Оплата за услуги");
        savedCash.setTransactionType("DEBIT");
        savedCash.setTransactionDate(LocalDateTime.now());
        caseRepository.save(savedCash);

        // Создаем новую транзакцию, связанную с сохраненным пользователем и кошельком
        Transaction savedTransaction = new Transaction(); //transfer
        savedTransaction.setDescription("При создании");
        savedTransaction.setUser(savedUser);
        savedTransaction.setCashAccount(savedCash);
        transactionRepository.save(savedTransaction);
        return savedUser;
    }

}
