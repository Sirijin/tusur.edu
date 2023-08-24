package ru.tusur.edu.service.achievement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tusur.edu.repository.achievement.AchievementRepository;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
}
