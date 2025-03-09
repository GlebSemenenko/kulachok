package com.kulachok.kulachok.repository;

import com.kulachok.kulachok.entity.Actor;
import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CashRepository extends JpaRepository<Cash, Integer> {
    Optional<Cash> findByUser(User user);
    Optional<Cash> findByActor(Actor actor);
}

