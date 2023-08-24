package ru.tusur.edu.web.controller.achievement;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.edu.service.achievement.AchievementService;

@RestController
@RequestMapping("/achievement")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;
}
