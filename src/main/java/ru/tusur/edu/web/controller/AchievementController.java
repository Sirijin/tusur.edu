package ru.tusur.edu.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.edu.service.achievement.AchievementService;

@RestController
@RequestMapping("/api/v1/achievement")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @GetMapping("/list")
    public ResponseEntity<?> findAllPageable() {
        return ResponseEntity.ok(achievementService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(achievementService.findById(id));
    }

}
