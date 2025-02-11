package com.kulachok.kulachok.repository;

import com.kulachok.kulachok.entity.Cash;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepository extends JpaRepository<Cash,Integer> {
}
