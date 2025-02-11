package com.kulachok.kulachok.service;

import com.kulachok.kulachok.entity.User;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {
    User addUser(@RequestBody User user);
}
