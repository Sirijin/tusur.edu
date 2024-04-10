package ru.tusur.edu.service.task;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
import ru.tusur.edu.util.UserUtil;
import ru.tusur.edu.web.packet.request.PageableRequest;
import ru.tusur.edu.web.packet.request.TaskSolutionRequest;
import ru.tusur.edu.web.packet.request.TestSolutionRequest;
import ru.tusur.edu.web.packet.response.TaskSolutionResponse;
import ru.tusur.edu.web.packet.response.TestSolutionResponse;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private static final Random random = new Random();
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
    public TaskSolutionResponse processUserSolution(Long taskId, TaskSolutionRequest request) {
        Task task = findOne(taskId);

        if (taskSolutionService.checkIfSolutionsAreCorrect(task, request)) {
            userTaskService.handleCorrectSolution(task, UserUtil.getUserId());
            return new TaskSolutionResponse("Решение верное!");
        } else {
            return new TaskSolutionResponse("Решение неверное");
        }
    }

    @SneakyThrows
    private Task findOne(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
    }

    @SneakyThrows
    public TaskResponse generateChallengingTest(int tasksAmount) {
        Set<Long> uniqueTaskIds = new HashSet<>();
        List<TaskDto> taskDtos = new ArrayList<>();
        while (uniqueTaskIds.size() < tasksAmount) {
            TaskDto taskDto = getRandomTask();
            if (uniqueTaskIds.add(taskDto.getId())) {
                taskDtos.add(taskDto);
            }
        }
        return TaskResponse.builder()
                .items(taskDtos)
                .total(taskDtos.size())
                .build();
    }

    public TaskDto getRandomTask() {
        List<TaskDto> allTasks = taskRepository.findAll().stream().map(taskMapper::toDto).toList();
        int randomIndex = random.nextInt(allTasks.size());
        return allTasks.get(randomIndex);
    }

    @SneakyThrows
    public TestSolutionResponse submitTest(TestSolutionRequest testSolutionRequest) {
        int correctAnswersCount = 0;
        if (testSolutionRequest.getSolutions() == null || testSolutionRequest.getSolutions().isEmpty()) {
            return new TestSolutionResponse(0);
        }
        Map<Long, String> solutions = testSolutionRequest.getSolutions();
        for (Map.Entry<Long, String> entry : solutions.entrySet()) {
            Long taskId = entry.getKey();
            String userAnswer = entry.getValue();

            Task task = findOne(taskId);
            TaskSolutionRequest taskSolutionRequest = new TaskSolutionRequest(List.of(userAnswer));

            if (taskSolutionService.checkIfSolutionsAreCorrect(task, taskSolutionRequest)) {
                correctAnswersCount++;
            }
        }

        return new TestSolutionResponse(correctAnswersCount);
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
