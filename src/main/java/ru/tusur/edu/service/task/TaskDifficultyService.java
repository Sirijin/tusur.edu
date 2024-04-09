package ru.tusur.edu.service.task;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.edu.entity.TaskDifficulty;
import ru.tusur.edu.repository.task.TaskDifficultyRepository;
import ru.tusur.edu.type.task.TaskDifficultyType;

import static ru.tusur.edu.util.EnumUtil.mapToEnumOrDefault;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskDifficultyService {

    private final TaskDifficultyRepository taskDifficultyRepository;

    @SneakyThrows
    public TaskDifficulty findByName(String name) {
        TaskDifficultyType difficulty = mapToEnumOrDefault(name, TaskDifficultyType.DIFFICULTY_UNKNOWN);
        return taskDifficultyRepository.findByName(difficulty);
    }
}
