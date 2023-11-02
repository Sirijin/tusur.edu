package ru.tusur.edu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.tusur.edu.dto.TaskDto;
import ru.tusur.edu.entity.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    @Mapping(target = "taskSolutions", ignore = true)
    Task toEntity(TaskDto dto);

    @Mapping(target = "taskThemeType", expression = "java(themeToString(entity.getTaskTheme()))")
    @Mapping(target = "taskDifficultyType", expression = "java(difficultyToString(entity.getTaskDifficulty()))")
    @Mapping(target = "taskLevelType", expression = "java(levelToString(entity.getTaskLevel()))")
    @Mapping(target = "taskSolutions", expression = "java(mapTaskSolutions(entity.getTaskSolutions()))")
    TaskDto toDto(Task entity);

    default String categoryToString(TaskCategory taskCategory) {
        return taskCategory.getName().name();
    }

    default String themeToString(TaskTheme taskTheme) {
        return taskTheme.getName().name();
    }

    default String difficultyToString(TaskDifficulty taskDifficulty) {
        return taskDifficulty.getName().name();
    }

    default String levelToString(TaskLevel taskLevel) {
        return taskLevel.getName().name();
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
