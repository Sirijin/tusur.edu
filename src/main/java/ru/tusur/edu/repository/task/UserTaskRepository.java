package ru.tusur.edu.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tusur.edu.entity.task.Task;
import ru.tusur.edu.entity.task.UserTask;

public interface UserTaskRepository extends JpaRepository<UserTask, Long> {
}
