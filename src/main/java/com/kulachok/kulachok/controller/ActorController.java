package com.kulachok.kulachok.controller;

import com.kulachok.kulachok.dto.ActorRequestDto;
import com.kulachok.kulachok.entity.Actor;
import com.kulachok.kulachok.repository.ActorRepository;
import com.kulachok.kulachok.service.ActorService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/kulachok/actris")
public class ActorController {

    private final ActorRepository actorRepository;
    private final ActorService actorService;

    public ActorController(ActorRepository actorRepository, ActorService actorService) {
        this.actorRepository = actorRepository;
        this.actorService = actorService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Actor>> getActor() {
        List<Actor> actorList = actorRepository.findAll();
        return ResponseEntity.ok(actorList);
    }

    @PostMapping("/add")
    public ResponseEntity<Actor> createActor(@Valid @RequestParam int userId, @RequestBody ActorRequestDto requestDto) {
        try {
            Actor savedActor = actorService.add(userId, requestDto.getActor(), requestDto.getFlmn());
            log.info("Actris saved: {}", savedActor.getNameActor().toString());
            return ResponseEntity.ok(savedActor);
        } catch (Exception e) {
            log.error("Error saving actris: {}", requestDto.getActor(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body((Actor) Map.of("error"
                            , "User not found", "ActorName"
                            , requestDto.getActor().toString()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Actor> updateActor(@Valid @PathVariable int id, @RequestBody ActorRequestDto requestDto) {
        if (actorRepository.existsById(id)) {
            Actor savedActor = actorService.update(id, requestDto.getActor(), requestDto.getFlmn());
            log.info("Actris with id {} updated", id);
            return ResponseEntity.ok(savedActor);
        } else {
            log.error("Actris with id {} not found for update", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body((Actor) Map.of("error", "User not found", "userId", id));
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
