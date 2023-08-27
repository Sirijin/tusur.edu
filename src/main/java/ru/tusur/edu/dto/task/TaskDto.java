package ru.tusur.edu.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tusur.edu.type.task.TaskCategoryType;
import ru.tusur.edu.type.task.TaskDifficultyType;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TaskDto {
    private String title;
    private String description;
    private TaskCategoryType taskCategory;
    private TaskDifficultyType taskDifficulty;
    private Instant addDate;
}
