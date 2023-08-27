package ru.tusur.edu.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tusur.edu.entity.task.TaskDifficulty;

public interface TaskDifficultyRepository extends JpaRepository<TaskDifficulty, Long> {
}
