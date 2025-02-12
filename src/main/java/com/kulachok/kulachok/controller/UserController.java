package com.kulachok.kulachok.controller;

import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.repository.UserRepository;
import com.kulachok.kulachok.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users") // Общий путь для контроллера
public class UserController {

    // todo поменяй внедрение зависимости
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/kulachok/getUsers")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/kulachok/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            // Сохраняем пользователя
            User savedUser = userService.addUser(user);
            log.info("User saved: {}", savedUser);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            log.error("Error saving user: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        if (userRepository.existsById(id)) {
            User existingUser = userRepository.findById(id).get();

            // Обновляем поля пользователя
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setEmail(updatedUser.getEmail());

            User savedUser = userRepository.save(existingUser);
            log.info("User with id {} updated", id);
            return ResponseEntity.ok(savedUser);
        } else {
            log.warn("User with id {} not found for update", id);
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("User with id {} deleted", id);
            return ResponseEntity.ok().build();
        } else {
            log.warn("User with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

}
