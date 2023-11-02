package ru.tusur.edu.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tusur.edu.entity.TaskCategory;
import ru.tusur.edu.type.task.TaskCategoryType;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Long> {

    TaskCategory findByName(TaskCategoryType name);
}
