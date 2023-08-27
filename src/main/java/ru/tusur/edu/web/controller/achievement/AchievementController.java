package ru.tusur.edu.web.controller.achievement;

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
    public ResponseEntity<?> findAchievements() {
        try {
            return ResponseEntity.ok(achievementService.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findAchievementById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(achievementService.findById(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
