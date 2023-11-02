package ru.tusur.edu.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tusur.edu.entity.TaskTheme;
import ru.tusur.edu.type.task.TaskCategoryType;
import ru.tusur.edu.type.task.TaskThemeType;

import java.util.List;

public interface TaskThemeRepository extends JpaRepository<TaskTheme, Long> {

    TaskTheme findByName(TaskThemeType name);

    List<TaskTheme> findByTaskCategoryName(TaskCategoryType categoryType);
}
