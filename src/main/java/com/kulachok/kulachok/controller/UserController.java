//package com.kulachok.kulachok.controller;
//
//import com.kulachok.kulachok.entity.User;
//import com.kulachok.kulachok.repository.UserRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Slf4j
//@RestController
//@RequestMapping("/users") // Общий путь для контроллера
//public class UserController {
//
//    // todo поменяй внедрение зависимости
//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping("/kulachok/getUsers")
//    public ResponseEntity<List<User>> getUsers() {
//        List<User> users = userRepository.findAll();
//        return ResponseEntity.ok(users);
//    }
//
//    @PostMapping("/kulachok/addUser")
//    public ResponseEntity<User> addUser(@RequestBody User user) {
//        User savedUser = userRepository.save(user);
//        log.info("User saved: " + savedUser);
//        return ResponseEntity.ok(savedUser); // Возвращаем сохраненный объект
//    }
//
//    @PutMapping("/{id}") // Метод для обновления пользователя по ID
//    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
//        if (userRepository.existsById(id)) {
//            updatedUser.setId(id); // Устанавливаем ID для обновления
//            User savedUser = userRepository.save(updatedUser);
//            log.info("User with id {} updated", id);
//            return ResponseEntity.ok(savedUser); // Возвращаем обновленный объект
//        } else {
//            log.warn("User with id {} not found for update", id);
//            return ResponseEntity.notFound().build(); // Возвращаем статус 404 Not Found
//        }
//    }
//
//    @DeleteMapping("/{id}") // Метод для удаления пользователя по ID
//    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
//        if (userRepository.existsById(id)) {
//            userRepository.deleteById(id);
//            log.info("User with id {} deleted", id);
//            return ResponseEntity.noContent().build(); // Возвращаем статус 204 No Content
//        } else {
//            log.warn("User with id {} not found", id);
//            return ResponseEntity.notFound().build(); // Возвращаем статус 404 Not Found
//        }
//    }
//}
