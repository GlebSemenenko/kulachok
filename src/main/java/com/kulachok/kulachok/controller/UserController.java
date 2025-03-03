package com.kulachok.kulachok.controller;

import com.kulachok.kulachok.dto.UserDto;
import com.kulachok.kulachok.entity.Actor;
import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.repository.UserRepository;
import exception.ResourceNotFoundException;
import com.kulachok.kulachok.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/kulachok/users") // Общий путь для контроллера
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@Valid @RequestBody UserDto user) {
        try {
            User savedUser = userService.add(user);
            log.info("User saved: {}, name: {}", savedUser.getId(), savedUser.getUsername());
            return ResponseEntity.ok(savedUser);
        } catch (Exception e){
            log.error("Error saving user: {}", user.getUsername());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body((User) Map.of("error"
                            , "User not found", "UserName"
                            , user.getUsername()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@Valid @PathVariable int id, @RequestBody UserDto user) {
        if (userRepository.existsById(id)) {
            User savedUser = userService.update(id, user);
            log.info("User with id {} updated", id);
            return ResponseEntity.ok(savedUser);
        } else {
            log.warn("User with id {} not found for update", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body((User) Map.of("error"
                            , "User not found", "UserName"
                            , user.getUsername()));
        }
    }

 @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) throws ResourceNotFoundException {
        if (userRepository.existsById(id)) {
            userService.deleteUserById(id);
            log.info("User with id {} deleted", id);
            return ResponseEntity.ok().build();
        } else {
            log.warn("User with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }


}
