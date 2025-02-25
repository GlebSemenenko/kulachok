package com.kulachok.kulachok.service;

import com.kulachok.kulachok.dto.UserDto;
import com.kulachok.kulachok.entity.User;

public interface UserService {
    User add(UserDto user);

    User update(int id, UserDto user);

    void deleteUserById(int userId) throws ResourceNotFoundException;
}
