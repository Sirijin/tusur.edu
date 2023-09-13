package ru.tusur.edu.service.task;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.edu.dto.TaskDto;
import ru.tusur.edu.dto.TaskResponse;
import ru.tusur.edu.entity.*;
import ru.tusur.edu.mapper.TaskMapper;
import ru.tusur.edu.repository.task.*;
import ru.tusur.edu.security.authentication.CustomPrincipal;
import ru.tusur.edu.security.entity.User;
import ru.tusur.edu.type.task.TaskCategoryType;
import ru.tusur.edu.type.task.TaskDifficultyType;
import ru.tusur.edu.web.packet.request.PageableRequest;
import ru.tusur.edu.web.packet.request.TaskSolutionRequest;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskCategoryRepository taskCategoryRepository;
    private final TaskDifficultyRepository taskDifficultyRepository;
    private final TaskSolutionRepository taskSolutionRepository;
    private final UserTaskRepository userTaskRepository;
    private final TaskMapper taskMapper;

    @SneakyThrows
    public TaskResponse findAllPageable(PageableRequest pageableRequest) {
        return TaskResponse
                .builder()
                .tasks(taskRepository.findAll(pageableRequest.getPageable()).getContent().stream().map(taskMapper::toDto).toList())
                .total(taskRepository.count())
                .build();
    }

    @SneakyThrows
    public TaskResponse findAllByCategory(String category, PageableRequest pageableRequest) {
        Page<Task> tasksPage = taskRepository.findByTaskCategory(TaskCategoryType.valueOf(category), pageableRequest.getPageable());
        return TaskResponse
                .builder()
                .tasks(tasksPage.getContent().stream().map(taskMapper::toDto).toList())
                .total(tasksPage.getTotalElements())
                .build();
    }

    @SneakyThrows
    public TaskResponse findAllByDifficulty(String difficulty, PageableRequest pageableRequest) {
        Page<Task> tasksPage = taskRepository.findByTaskDifficulty(TaskDifficultyType.valueOf(difficulty), pageableRequest.getPageable());
        return TaskResponse
                .builder()
                .tasks(tasksPage.getContent().stream().map(taskMapper::toDto).toList())
                .total(tasksPage.getTotalElements())
                .build();
    }

    @SneakyThrows
    @Transactional
    public TaskDto findOne(Long taskId) {
        Long userId = getUserId();
        if (!userTaskRepository.existsByUserIdAndTaskId(userId, taskId)) {
            initializeUserTask(userId, taskId);
        }
        return taskRepository.findById(taskId).map(taskMapper::toDto).orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));
    }

    @SneakyThrows
    @Transactional
    public TaskDto save(TaskDto dto) {
        Task task = taskMapper.toEntity(dto);

        TaskCategoryType category = mapToEnumOrDefault(dto.getTaskCategoryType(), TaskCategoryType.CATEGORY_UNKNOWN);
        task.setTaskCategory(taskCategoryRepository.findByName(category));

        TaskDifficultyType difficulty = mapToEnumOrDefault(dto.getTaskDifficultyType(), TaskDifficultyType.DIFFICULTY_UNKNOWN);
        task.setTaskDifficulty(taskDifficultyRepository.findByName(difficulty));

        task = taskRepository.save(task);

        Task finalTask = task;
        List<TaskSolution> taskSolutions = dto.getTaskSolutions()
                .stream()
                .map(solution -> TaskSolution.builder().solution(solution).task(finalTask).build())
                .toList();

        taskSolutionRepository.saveAll(taskSolutions);

        return taskMapper.toDto(task);
    }

    @SneakyThrows
    @Transactional
    public TaskDto update(Long taskId, TaskDto updatedDto) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        updateFields(updatedDto, existingTask);

        return taskMapper.toDto(taskRepository.save(existingTask));
    }

    @Transactional
    @SneakyThrows
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @SneakyThrows
    public ResponseEntity<?> sendSolution(Long taskId, TaskSolutionRequest solutionRequest) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (taskOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Task task = taskOptional.get();

        List<String> correctAnswers = task.getTaskSolutions()
                .stream()
                .map(TaskSolution::getSolution)
                .toList();

        List<String> userAnswers = solutionRequest.getSolution();

        if (correctAnswers.equals(userAnswers)) {
            handleCorrectSolution(task.getId(), getUserId());
            return ResponseEntity.ok("Решение верное!");
        } else {
            return ResponseEntity.ok("Решение неверное");
        }
    }

    private void initializeUserTask(Long userId, Long taskId) {
        UserTask newUserTask = UserTask.builder()
                .user(userTaskRepository.findUserByUserId(userId))
                .task(userTaskRepository.findTaskByTaskId(taskId))
                .completeDate(null)
                .build();

        userTaskRepository.save(newUserTask);
    }

    private static Long getUserId() {
        return ((CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    private void handleCorrectSolution(Long taskId, Long userId) {
        UserTask userTask = userTaskRepository.findByUserIdAndTaskId(userId, taskId)
                .orElseThrow(() -> new RuntimeException("User has not started this task yet"));

        userTask.setCompleteDate(new Timestamp(System.currentTimeMillis()));
        userTask.setIsFinished(true);
        userTaskRepository.save(userTask);

        User user = userTask.getUser();
        user.increaseBalanceForTask();
        user.increaseDailyActivityForTask();
    }

    private void updateFields(TaskDto updatedDto, Task existingTask) {
        if (!existingTask.getTitle().equals(updatedDto.getTitle())) {
            existingTask.setTitle(updatedDto.getTitle());
        }

        if (!existingTask.getDescription().equals(updatedDto.getDescription())) {
            existingTask.setDescription(updatedDto.getDescription());
        }

        TaskCategoryType categoryType = mapToEnumOrDefault(updatedDto.getTaskCategoryType(), TaskCategoryType.CATEGORY_UNKNOWN);
        TaskCategory newCategory = taskCategoryRepository.findByName(categoryType);

        if (newCategory != null && !existingTask.getTaskCategory().equals(newCategory)) {
            existingTask.setTaskCategory(newCategory);
        }

        TaskDifficultyType difficultyType = mapToEnumOrDefault(updatedDto.getTaskDifficultyType(), TaskDifficultyType.DIFFICULTY_UNKNOWN);
        TaskDifficulty newDifficulty = taskDifficultyRepository.findByName(difficultyType);

        if (newDifficulty != null && !existingTask.getTaskDifficulty().equals(newDifficulty)) {
            existingTask.setTaskDifficulty(newDifficulty);
        }
    }

    private <T extends Enum<T>> T mapToEnumOrDefault(String value, T defaultValue) {
        try {
            return Enum.valueOf(defaultValue.getDeclaringClass(), value);
        } catch (IllegalArgumentException | NullPointerException e) {
            return defaultValue;
        }
    }
}
