package com.kulachok.kulachok.controller;

import com.kulachok.kulachok.dto.CashDto;
import com.kulachok.kulachok.entity.Actris;
import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.model_Interface.CashAccountHolder;
import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.repository.ActrisRepository;
import com.kulachok.kulachok.repository.UserRepository;
import com.kulachok.kulachok.service.CashService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/kulachok/cash")
public class CashController {

    private final UserRepository userRepository;

    private final CashService cashService;

    private final ActrisRepository actrisRepository;

    public CashController(UserRepository userRepository, CashService cashService, ActrisRepository actrisRepository) {
        this.userRepository = userRepository;
        this.cashService = cashService;
        this.actrisRepository = actrisRepository;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Cash> updateCash(
            @PathVariable int id,
            @RequestParam String accountType,
            @RequestBody CashDto cash) {

        Class<? extends CashAccountHolder> userType;

        if ("user".equalsIgnoreCase(accountType)) {
            if (!userRepository.existsById(id)) {
                return ResponseEntity.ok().build();
            }
            userType = User.class;
            log.info("Checking if user exists with id: {}", userRepository.existsById(id));
        } else if ("actris".equalsIgnoreCase(accountType)) {
            if (!actrisRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            userType = Actris.class;
            log.info("Checking if actress exists with id and name: {}", actrisRepository.existsById(id));
        } else {
            return ResponseEntity.ok().build();
        }

        Cash savedCash = cashService.updateCash(id, cash, userType);
        return ResponseEntity.ok(savedCash);
    }

}


