package ru.tusur.edu.mapper;

import org.springframework.stereotype.Component;
import ru.tusur.edu.dto.task.TaskDto;
import ru.tusur.edu.entity.task.Task;


@Component
public class TaskMapper {

    public TaskDto map(Task task) {
        return TaskDto
                .builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .taskCategory(task.getTaskCategory().getName())
                .taskDifficulty(task.getTaskDifficulty().getName())
                .addDate(task.getAddDate())
                .build();
    }
}
