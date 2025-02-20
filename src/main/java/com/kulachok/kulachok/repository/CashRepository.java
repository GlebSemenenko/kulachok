package com.kulachok.kulachok.repository;

import com.kulachok.kulachok.entity.Actris;
import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.CashAccountHolder;
import com.kulachok.kulachok.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashRepository extends JpaRepository<Cash, Integer> {
    Cash findByActris(Actris actris);
    Cash findByUser(User user);
}

