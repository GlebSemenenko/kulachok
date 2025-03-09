package com.kulachok.kulachok.controller;


import com.kulachok.kulachok.dto.VideoDto;
import com.kulachok.kulachok.entity.Video;
import com.kulachok.kulachok.repository.VideoRepository;
import com.kulachok.kulachok.service.VideoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/kulachok/video")
public class VideoController {
    private final VideoRepository videoRepository;
    private final VideoService videoService;
    private final ValidationHandler validationHandler; // Добавление обработчика валидации

    @Autowired
    public VideoController(VideoRepository videoRepository, VideoService videoService, ValidationHandler validationHandler) {
        this.videoRepository = videoRepository;
        this.videoService = videoService;
        this.validationHandler = validationHandler;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Video>> getVideos() {
        List<Video> videos = videoRepository.findAll();
        return ResponseEntity.ok(videos);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addVideo(@RequestParam int id,
                                           @Valid @RequestBody VideoDto videoDto,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> errorMap = validationHandler.handleValidationErrors(bindingResult);
            log.error("Error saving video: {}", videoDto, bindingResult);
            return ResponseEntity.badRequest().body(errorMap);
        } else {
            Video savedVideo = videoService.addVideo(id, videoDto);
            log.info("Video saved: {}", savedVideo.getId());
            return ResponseEntity.ok(savedVideo);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateVideo(@PathVariable Integer id, @Valid @RequestBody VideoDto videoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> errorMap = validationHandler.handleValidationErrors(bindingResult);
            log.error("Error updating video: {}", videoDto, bindingResult);
            return ResponseEntity.badRequest().body(errorMap);
        } else if (!videoRepository.existsById(id)) {
            log.warn("Video with id {} not found for update", id);
            return ResponseEntity.notFound().build();
        } else {
            Video updatedVideo = videoService.updateVideo(id, videoDto);
            log.info("Video with id {} updated", id);
            return ResponseEntity.ok(updatedVideo);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Integer id) {
        if (!videoRepository.existsById(id)) {
            log.warn("Video with id {} not found for deletion", id);
            return ResponseEntity.notFound().build();
        }
        videoService.deleteVideo(id);
        log.info("Video with id {} deleted", id);
        return ResponseEntity.noContent().build();
    }
}
