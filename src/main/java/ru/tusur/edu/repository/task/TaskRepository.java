package ru.tusur.edu.repository.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tusur.edu.entity.Task;
import ru.tusur.edu.type.task.TaskCategoryType;
import ru.tusur.edu.type.task.TaskDifficultyType;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select t from Task t where t.taskCategory.name = ?1")
    Page<Task> findByTaskCategory(TaskCategoryType name, Pageable pageable);

    @Query("select t from Task t where t.taskDifficulty.name = ?1")
    Page<Task> findByTaskDifficulty(TaskDifficultyType name, Pageable pageable);
}
