package com.kulachok.kulachok.repository;

import com.kulachok.kulachok.entity.Actor;
import com.kulachok.kulachok.entity.Transfer;
import com.kulachok.kulachok.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Integer> {
    List<Transfer> findByUser(User accountHolder);

    List<Transfer> findByActor(Actor accountHolder);
}
