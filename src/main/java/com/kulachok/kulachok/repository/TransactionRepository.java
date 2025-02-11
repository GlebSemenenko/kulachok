package com.kulachok.kulachok.repository;

import com.kulachok.kulachok.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
