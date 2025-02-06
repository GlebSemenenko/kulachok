//package com.kulachok.kulachok.controller;
//
//import com.kulachok.kulachok.entity.Actris;
//import com.kulachok.kulachok.repository.ActrisRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Slf4j
//@RestController
//public class ArtistController {
//
//    // todo поменяй внедрение зависимости
//    @Autowired
//    private ActrisRepository actrisRepository;
//
//    @GetMapping
//    public ResponseEntity<List<Actris>> getActris() {
//        List<Actris> actrisList = actrisRepository.findAll();
//        return ResponseEntity.ok(actrisList);
//    }
//
//    @PostMapping()
//    public void addArtist(@RequestBody Actris actris) {
//        log.info("Actris saved: " + actrisRepository.save(actris));
//    }
//
//    @PutMapping("/{id}") // Метод для обновления актрисы по ID
//    public ResponseEntity<Actris> updateArtist(@PathVariable int id, @RequestBody Actris updatedActris) {
//        if (actrisRepository.existsById(id)) {
//            updatedActris.setId(id); // Устанавливаем ID для обновления
//            Actris savedActris = actrisRepository.save(updatedActris);
//            log.info("Actris with id {} updated", id);
//            return ResponseEntity.ok(savedActris); // Возвращаем обновленный объект
//        } else {
//            log.warn("Actris with id {} not found for update", id);
//            return ResponseEntity.notFound().build(); // Возвращаем статус 404 Not Found
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteArtist(@PathVariable int id) {
//        if (actrisRepository.existsById(id)) {
//            actrisRepository.deleteById(id);
//            log.info("Actris with id {} deleted", id);
//            return ResponseEntity.noContent().build(); // Возвращаем статус 204 No Content
//        } else {
//            log.warn("Actris with id {} not found", id);
//            return ResponseEntity.notFound().build(); // Возвращаем статус 404 Not Found
//        }
//    }
//}
