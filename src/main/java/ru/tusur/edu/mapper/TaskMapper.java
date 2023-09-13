package ru.tusur.edu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tusur.edu.dto.TaskDto;
import ru.tusur.edu.entity.Task;
import ru.tusur.edu.entity.TaskCategory;
import ru.tusur.edu.entity.TaskDifficulty;
import ru.tusur.edu.entity.TaskSolution;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userTasks", ignore = true)
    @Mapping(target = "taskCategory", ignore = true)
    @Mapping(target = "taskDifficulty", ignore = true)
    @Mapping(target = "taskSolutions", ignore = true)
    Task toEntity(TaskDto dto);

    @Mapping(target = "taskCategoryType", expression = "java(categoryToString(entity.getTaskCategory()))")
    @Mapping(target = "taskDifficultyType", expression = "java(difficultyToString(entity.getTaskDifficulty()))")
    @Mapping(target = "taskSolutions", expression = "java(mapTaskSolutions(entity.getTaskSolutions()))")
    TaskDto toDto(Task entity);

    default String categoryToString(TaskCategory taskCategory) {
        return taskCategory.getName().name();
    }

    default String difficultyToString(TaskDifficulty taskDifficulty) {
        return taskDifficulty.getName().name();
    }

    default List<String> mapTaskSolutions(Set<TaskSolution> taskSolutions) {
        if (taskSolutions == null) {
            return Collections.emptyList();
        }
        return taskSolutions.stream()
                .map(TaskSolution::getSolution)
                .toList();
    }
}
