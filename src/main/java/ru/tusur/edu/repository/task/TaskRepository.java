package ru.tusur.edu.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tusur.edu.entity.task.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
