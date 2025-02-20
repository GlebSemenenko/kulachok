package com.kulachok.kulachok.repository;

import com.kulachok.kulachok.entity.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Integer> {
}
