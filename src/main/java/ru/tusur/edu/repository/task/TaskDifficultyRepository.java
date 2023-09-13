package ru.tusur.edu.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tusur.edu.entity.TaskDifficulty;
import ru.tusur.edu.type.task.TaskDifficultyType;

public interface TaskDifficultyRepository extends JpaRepository<TaskDifficulty, Long> {

    TaskDifficulty findByName(TaskDifficultyType name);
}
