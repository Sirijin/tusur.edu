package ru.tusur.edu.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tusur.edu.entity.task.TaskCategory;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Long> {
}
