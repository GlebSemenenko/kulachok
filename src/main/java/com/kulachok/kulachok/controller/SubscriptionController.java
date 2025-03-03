package com.kulachok.kulachok.controller;

import com.kulachok.kulachok.entity.Actor;
import com.kulachok.kulachok.entity.Subscription;
import com.kulachok.kulachok.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/kulachok/subscription")
public class SubscriptionController {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionController(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<Subscription>> getSubscriptions(@PathVariable int id) {
        if (!subscriptionRepository.existsById(id)) {
            List<Subscription> subscriptions = subscriptionRepository.findByActorId(id);
            return ResponseEntity.ok(subscriptions);
        } else {
            log.error("Error getting subscriptions for actor with id {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Subscription> updateSubscription(@RequestBody Subscription subscription) {
        try {
            subscription.setSubscriptionDate(LocalDateTime.now());
            Subscription savedSubscription = subscriptionRepository.save(subscription);
            log.info("Subscription saved: {}", savedSubscription);
            return ResponseEntity.ok(savedSubscription);
        } catch (Exception e) {
            log.error("Error saving subscription: {}", subscription);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body((Subscription)
                            Map.of("error", "Subscription not found"
                                    , "ActorId", subscription.getId()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable int id) {
        try {
            subscriptionRepository.deleteById(id);
            log.info("Subscription with id {} deleted", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting subscription with id {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}
