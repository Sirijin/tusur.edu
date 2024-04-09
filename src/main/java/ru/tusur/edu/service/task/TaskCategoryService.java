package ru.tusur.edu.service.task;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.edu.entity.TaskCategory;
import ru.tusur.edu.repository.task.TaskCategoryRepository;
import ru.tusur.edu.type.task.TaskCategoryType;

import static ru.tusur.edu.util.EnumUtil.mapToEnumOrDefault;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskCategoryService {

    private final TaskCategoryRepository taskCategoryRepository;

    @SneakyThrows
    public TaskCategory findByName(String name) {
        TaskCategoryType category = mapToEnumOrDefault(name, TaskCategoryType.CATEGORY_UNKNOWN);
        return taskCategoryRepository.findByName(category);
    }
}
