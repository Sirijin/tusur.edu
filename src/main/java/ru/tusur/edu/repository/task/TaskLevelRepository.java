package ru.tusur.edu.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tusur.edu.entity.TaskLevel;
import ru.tusur.edu.type.task.TaskLevelType;

public interface TaskLevelRepository extends JpaRepository<TaskLevel, Long> {

    TaskLevel findByName(TaskLevelType name);
}
