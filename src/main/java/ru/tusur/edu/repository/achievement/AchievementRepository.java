package ru.tusur.edu.repository.achievement;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tusur.edu.entity.Achievement;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
}
