package ru.tusur.edu.mapper;

import org.springframework.stereotype.Component;
import ru.tusur.edu.dto.AchievementDto;
import ru.tusur.edu.entity.Achievement;

@Component
public class AchievementMapper {

    public Achievement map(AchievementDto dto) {
        return Achievement
                .builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
    }

    public AchievementDto map(Achievement achievement) {
        return AchievementDto
                .builder()
                .title(achievement.getTitle())
                .description(achievement.getDescription())
                .build();
    }
}
