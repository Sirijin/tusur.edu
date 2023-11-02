package ru.tusur.edu.service.task;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.edu.dto.TaskDto;
import ru.tusur.edu.dto.TaskResponse;
import ru.tusur.edu.entity.Task;
import ru.tusur.edu.entity.TaskDifficulty;
import ru.tusur.edu.entity.TaskLevel;
import ru.tusur.edu.entity.TaskTheme;
import ru.tusur.edu.mapper.TaskMapper;
import ru.tusur.edu.repository.task.TaskRepository;
import ru.tusur.edu.type.task.TaskCategoryType;
import ru.tusur.edu.util.UserUtil;
import ru.tusur.edu.web.packet.request.PageableRequest;
import ru.tusur.edu.web.packet.request.TaskSolutionRequest;

import java.util.*;
import java.util.stream.Collectors;

import static ru.tusur.edu.type.task.TaskCategoryType.CATEGORY_UNKNOWN;
import static ru.tusur.edu.type.task.TaskCategoryType.values;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final TaskDifficultyService taskDifficultyService;
    private final TaskThemeService taskThemeService;
    private final TaskLevelService taskLevelService;
    private final TaskSolutionService taskSolutionService;
    private final UserTaskService userTaskService;

    @SneakyThrows
    @Transactional(readOnly = true)
    public TaskResponse findAllPageable(PageableRequest pageableRequest) {
        return TaskResponse
                .builder()
                .items(taskRepository.findAll(pageableRequest.getPageable()).getContent().stream().map(taskMapper::toDto).toList())
                .total(taskRepository.count())
                .build();
    }

    @SneakyThrows
    public TaskDto findOneDto(Long taskId) {
        userTaskService.checkIfUserStartedTask(taskId);
        return taskRepository.findById(taskId).map(taskMapper::toDto).orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));
    }

    @SneakyThrows
    public TaskDto save(TaskDto dto) {
        Task task = taskMapper.toEntity(dto);

        task.setTaskTheme(taskThemeService.findByName(dto.getTaskThemeType()));
        task.setTaskDifficulty(taskDifficultyService.findByName(dto.getTaskDifficultyType()));
        task.setTaskLevel(taskLevelService.findByName(dto.getTaskLevelType()));
        taskRepository.save(task);
        taskSolutionService.saveTaskSolutions(task, dto.getTaskSolutions());

        return taskMapper.toDto(task);
    }

    @SneakyThrows
    public List<TaskDto> saveList(List<TaskDto> dtos) {
        return dtos.stream().map(this::save).toList();
    }

    @SneakyThrows
    public TaskDto update(Long taskId, TaskDto updatedDto) {
        Task task = findOne(taskId);

        updateFields(updatedDto, task);

        return taskMapper.toDto(taskRepository.save(task));
    }

    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @SneakyThrows
    public ResponseEntity<?> processUserSolution(Long taskId, TaskSolutionRequest request) {
        Task task = findOne(taskId);

        if (taskSolutionService.checkIfSolutionsAreCorrect(task, request)) {
            userTaskService.handleCorrectSolution(task, UserUtil.getUserId());
            return ResponseEntity.ok("Решение верное!");
        } else {
            return ResponseEntity.ok("Решение неверное");
        }
    }

    @SneakyThrows
    private Task findOne(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
    }

    @SneakyThrows
    public TaskResponse generateChallengingTest(int tasksAmount) {
        Long userId = UserUtil.getUserId();
        List<TaskCategoryType> categoryTypes = Arrays.stream(values())
                .filter(categoryType -> categoryType != CATEGORY_UNKNOWN)
                .toList();

        return TaskResponse.builder()
                .items(categoryTypes.stream()
                        .map(categoryType -> {
                            TaskTheme theme = findThemeWithFewestSolvedTasks(categoryType, userId);
                            Task task = findRandomTaskInTheme(theme);
                            return taskMapper.toDto(task);
                        })
                        .limit(tasksAmount)
                        .collect(Collectors.toList()))
                .total(tasksAmount).build();
    }

    private TaskTheme findThemeWithFewestSolvedTasks(TaskCategoryType categoryType, Long userId) {
        List<TaskTheme> themesInCategory = taskThemeService.findThemesInCategory(categoryType);

        if (themesInCategory.isEmpty()) {
            return null;
        }

        Map<TaskTheme, Long> themeToSolvedCount = new HashMap<>();

        for (TaskTheme theme : themesInCategory) {
            Long solvedCount = userTaskService.countSolvedTasksByUserIdAndTheme(userId, theme);
            themeToSolvedCount.put(theme, solvedCount);
        }
        return Collections.min(
                themeToSolvedCount.entrySet(),
                Map.Entry.comparingByValue()
        ).getKey();
    }

    private Task findRandomTaskInTheme(TaskTheme theme) {
        List<Task> tasksInTheme = taskRepository.findByTaskTheme(theme);
        if (tasksInTheme.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(tasksInTheme.size());

        return tasksInTheme.get(randomIndex);
    }

    public Object submitTest() {

        return null;
    }

    private void updateFields(TaskDto updatedDto, Task existingTask) {
        if (!existingTask.getTitle().equals(updatedDto.getTitle())) {
            existingTask.setTitle(updatedDto.getTitle());
        }

        if (!existingTask.getDescription().equals(updatedDto.getDescription())) {
            existingTask.setDescription(updatedDto.getDescription());
        }

        TaskTheme newTheme = taskThemeService.findByName(updatedDto.getTaskThemeType());
        if (!existingTask.getTaskTheme().equals(newTheme)) {
            existingTask.setTaskTheme(newTheme);
        }

        TaskDifficulty newDifficulty = taskDifficultyService.findByName(updatedDto.getTaskDifficultyType());
        if (!existingTask.getTaskDifficulty().equals(newDifficulty)) {
            existingTask.setTaskDifficulty(newDifficulty);
        }

        TaskLevel newLevel = taskLevelService.findByName(updatedDto.getTaskLevelType());
        if (!existingTask.getTaskLevel().equals(newLevel)) {
            existingTask.setTaskLevel(newLevel);
        }
    }
}
