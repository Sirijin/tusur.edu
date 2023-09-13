package ru.tusur.edu.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tusur.edu.entity.TaskSolution;

public interface TaskSolutionRepository extends JpaRepository<TaskSolution, Long> {
}
