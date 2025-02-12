package com.kulachok.kulachok.service;

import com.kulachok.kulachok.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {
    User addUser(@RequestBody User user);

    User update(@PathVariable int id, @RequestBody User updatedUser);
}
