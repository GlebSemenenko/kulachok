package com.kulachok.kulachok.repository;

import com.kulachok.kulachok.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    List<Subscription> findByActorId(int id);
}
