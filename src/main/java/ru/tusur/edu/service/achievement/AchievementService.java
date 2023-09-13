package ru.tusur.edu.service.achievement;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.tusur.edu.dto.AchievementDto;
import ru.tusur.edu.dto.AchievementResponse;
import ru.tusur.edu.mapper.AchievementMapper;
import ru.tusur.edu.repository.achievement.AchievementRepository;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;

    @SneakyThrows
    public AchievementResponse findAll() {
        return AchievementResponse
                .builder()
                .achievements(achievementRepository.findAll().stream().map(achievementMapper::map).toList())
                .total(achievementRepository.count())
                .build();
    }

    @SneakyThrows
    public AchievementDto findById(Long id) {
        return achievementRepository.findById(id).map(achievementMapper::map).orElse(null);
    }

}
