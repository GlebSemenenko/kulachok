package com.kulachok.kulachok.controller;

import com.kulachok.kulachok.entity.Actris;
import com.kulachok.kulachok.entity.Cash;
import com.kulachok.kulachok.entity.User;
import com.kulachok.kulachok.repository.ActrisRepository;
import com.kulachok.kulachok.service.ActrisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/kulachok/actris")
public class ArtistController {

    // todo поменяй внедрение зависимости
    private final ActrisRepository actrisRepository;
    private final ActrisService actrisService;

    public ArtistController(ActrisRepository actrisRepository, ActrisService actrisService) {
        this.actrisRepository = actrisRepository;
        this.actrisService = actrisService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Actris>> getActris() {
        List<Actris> actrisList = actrisRepository.findAll();
        return ResponseEntity.ok(actrisList);
    }

    @PostMapping("/add")
    public ResponseEntity<Actris> createActris(@RequestBody Actris actris, @RequestParam int userId) {
        try {
            Actris savedActris = actrisService.add(actris, userId);
            log.info("Actris saved: {}", savedActris);
            return ResponseEntity.ok(savedActris);
        } catch (Exception e) {
            log.error("Error saving actris: {}", actris, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/{id}") // Метод для обновления актрисы по ID
    public ResponseEntity<Actris> updateArtist(@PathVariable int id, @RequestBody Actris actris) {
        if (actrisRepository.existsById(id)) {
            Actris savedActris = actrisService.update(id, actris);
            log.info("Actris with id {} updated", id);
            return ResponseEntity.ok(savedActris);
        } else {
            log.warn("Actris with id {} not found for update", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable int id) {
        if (actrisRepository.existsById(id)) {
            actrisRepository.deleteById(id);
            log.info("Actris with id {} deleted", id);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("Actris with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}
