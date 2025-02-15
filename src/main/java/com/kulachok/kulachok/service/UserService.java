package com.kulachok.kulachok.service;

import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

public interface UserService {
    User add(@RequestBody User user);

    User update(@PathVariable int id, @RequestBody User updatedUser);

    Cash updateCash(@PathVariable int userId, @RequestBody BigDecimal amount);
}
