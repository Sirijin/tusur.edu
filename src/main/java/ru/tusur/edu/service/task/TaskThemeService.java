package ru.tusur.edu.service.task;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.edu.entity.TaskTheme;
import ru.tusur.edu.repository.task.TaskThemeRepository;
import ru.tusur.edu.type.task.TaskCategoryType;
import ru.tusur.edu.type.task.TaskThemeType;

import java.util.List;

import static ru.tusur.edu.util.EnumUtil.mapToEnumOrDefault;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskThemeService {

    private final TaskThemeRepository taskThemeRepository;

    @SneakyThrows
    public TaskTheme findByName(String name) {
        TaskThemeType theme = mapToEnumOrDefault(name, TaskThemeType.THEME_UNKNOWN);
        return taskThemeRepository.findByName(theme);
    }

    @SneakyThrows
    public List<TaskTheme> findThemesInCategory(TaskCategoryType categoryType) {
        return taskThemeRepository.findByTaskCategoryName(categoryType);
    }

}
