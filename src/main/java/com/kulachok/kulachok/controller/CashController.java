package com.kulachok.kulachok.controller;

import com.kulachok.kulachok.dto.CashDto;
import com.kulachok.kulachok.entity.Actor;
import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.model_Interface.CashAccountHolder;
import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.repository.ActorRepository;
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

    private final CashService cashService;

    public CashController(CashService cashService) {
        this.cashService = cashService;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Cash> updateCash(
            @PathVariable int id,
            @RequestParam String accountType,
            @RequestBody CashDto cash) {
        Cash savedCash = cashService.recordWalletValue(id, cash, accountType);
        return ResponseEntity.ok(savedCash);
    }
}


