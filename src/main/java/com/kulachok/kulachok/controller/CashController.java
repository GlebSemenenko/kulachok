package com.kulachok.kulachok.controller;

import com.kulachok.kulachok.dto.CashDto;
import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.service.CashService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @Autowired
    public CashController(CashService cashService) {
        this.cashService = cashService;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Cash> addCash(@Valid @PathVariable int id,
                                        @RequestParam String accountType,
                                        @RequestBody CashDto cash,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Error saving cash: {}", cash);
            return ResponseEntity.badRequest().build();
        } else {
            Cash savedCash = cashService.recordWalletValue(id, cash, accountType);
            return ResponseEntity.ok(savedCash);
        }
    }
}


