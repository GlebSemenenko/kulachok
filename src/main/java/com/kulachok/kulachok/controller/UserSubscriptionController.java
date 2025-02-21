package com.kulachok.kulachok.controller;

import com.kulachok.kulachok.entity.UserSubscription;
import com.kulachok.kulachok.repository.UserSubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/kulachok/subscription")
//todo изменить имя
public class UserSubscriptionController {

    private final UserSubscriptionRepository usrSubscriptionRepository;

    public UserSubscriptionController(UserSubscriptionRepository usrSubscriptionRepository) {
        this.usrSubscriptionRepository = usrSubscriptionRepository;
        log.info("UserSubscriptionController created");
    }

    @GetMapping("/get/{id}")
    public Optional<UserSubscription> getUserSubscriptions(@PathVariable int id) {
        log.info("UserSubscriptionController getting user subscription with id {}", id);
        return usrSubscriptionRepository.findById(id);
    }

    @PutMapping("/add")
    public UserSubscription updateUserSubscription(@RequestBody UserSubscription userSubscription) {
        log.info("UserSubscriptionController updating user subscription with id {}", userSubscription.getId());
        return usrSubscriptionRepository.save(userSubscription);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserSubscription(@PathVariable int id) {
        log.info("UserSubscriptionController deleting user subscription with id {}", id);
        usrSubscriptionRepository.deleteById(id);
    }
}
