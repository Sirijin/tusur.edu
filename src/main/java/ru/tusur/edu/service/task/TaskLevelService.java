package ru.tusur.edu.service.task;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.edu.entity.TaskLevel;
import ru.tusur.edu.repository.task.TaskLevelRepository;
import ru.tusur.edu.type.task.TaskLevelType;

import static ru.tusur.edu.util.EnumUtil.mapToEnumOrDefault;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskLevelService {

    private final TaskLevelRepository taskLevelRepository;

    @SneakyThrows
    public TaskLevel findByName(String name) {
        TaskLevelType level = mapToEnumOrDefault(name, TaskLevelType.LEVEL_UNKNOWN);
        return taskLevelRepository.findByName(level);
    }
}
