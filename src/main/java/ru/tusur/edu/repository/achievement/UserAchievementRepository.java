package ru.tusur.edu.repository.achievement;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tusur.edu.entity.UserAchievement;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {
}
