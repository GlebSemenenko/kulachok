package com.kulachok.kulachok.controller;

import com.kulachok.kulachok.dto.ActorDto;
import com.kulachok.kulachok.entity.Actor;
import com.kulachok.kulachok.repository.ActorRepository;
import com.kulachok.kulachok.service.ActorService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.ValidationHandler;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/kulachok/actris")
public class ActorController {
    private final ActorRepository actorRepository;
    private final ActorService actorService;
    private final ValidationHandler validationHandler;

    @Autowired
    public ActorController(ActorRepository actorRepository, ActorService actorService, ValidationHandler validationHandler) {
        this.actorRepository = actorRepository;
        this.actorService = actorService;
        this.validationHandler = validationHandler;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Actor>> getActor() {
        List<Actor> actorList = actorRepository.findAll();
        return ResponseEntity.ok(actorList);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createActor(@RequestParam int userId
            , @Valid @RequestBody ActorDto actorDto
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> errorMap = validationHandler.handleValidationErrors(bindingResult);
            log.error("Error saving actor: {}", actorDto.getNameActor());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        } else {
            Actor savedActor = actorService.add(userId, actorDto);
            log.info("Actris saved: {}", savedActor.getNameActor().toString());
            return ResponseEntity.ok(savedActor);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateActor(@Valid @PathVariable int id,
                                              @Valid @RequestBody ActorDto actorDto,
                                              BindingResult bindingResult) {
        if (actorRepository.existsById(id)) {
            if (bindingResult.hasErrors()) {
                Map<String, Object> errorMap = validationHandler.handleValidationErrors(bindingResult);
                log.error("Error saving actor: {}", actorDto.getNameActor());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
            } else {
                Actor savedActor = actorService.update(id, actorDto);
                log.info("Actris with id {} updated", id);
                return ResponseEntity.ok(savedActor);
            }
        } else {
            log.error("Actris with id {} not found for update", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found", "userId", id));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable int id) {
        if (actorRepository.existsById(id)) {
            actorRepository.deleteById(id);
            log.info("Actris with id {} deleted", id);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("Actris with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}
