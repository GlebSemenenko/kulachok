package com.kulachok.kulachok.controller;

import com.kulachok.kulachok.dto.UserDto;
import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.repository.UserRepository;
import com.kulachok.kulachok.service.UserService;
import exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.ValidationHandler;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/kulachok/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final ValidationHandler validationHandler;

    @Autowired
    public UserController(UserRepository userRepository,
                          UserService userService,
                          ValidationHandler validationHandler) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.validationHandler = validationHandler;
    }

    @GetMapping("/get")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserDto user,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> errorMap = validationHandler.handleValidationErrors(bindingResult);
            log.error("Error saving user: {}", user.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        } else {
            User savedUser = userService.add(user);
            log.info("User saved: {}, name: {}", savedUser.getId(), savedUser.getUsername());
            return ResponseEntity.ok(savedUser);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable int id,
                                             @Valid @RequestBody UserDto user,
                                             BindingResult bindingResult) {
        if (userRepository.existsById(id)) {
            if (bindingResult.hasErrors()) {
                Map<String, Object> errorMap = validationHandler.handleValidationErrors(bindingResult);
                log.error("Error updating user: {}", id);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
            } else {
                User savedUser = userService.update(id, user);
                log.info("User with id {} updated", id);
                return ResponseEntity.ok(savedUser);
            }
        } else {
            log.warn("User with id {} not found for update", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error"
                            , "User not found", "UserName"
                            , user.getUsername()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) throws ResourceNotFoundException {
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
