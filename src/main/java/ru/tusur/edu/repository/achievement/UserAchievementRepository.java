package ru.tusur.edu.repository.achievement;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tusur.edu.entity.achievement.Achievement;
import ru.tusur.edu.entity.achievement.UserAchievement;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {
}
