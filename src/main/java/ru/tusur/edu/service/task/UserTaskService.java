package ru.tusur.edu.service.task;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.edu.entity.Task;
import ru.tusur.edu.entity.TaskTheme;
import ru.tusur.edu.entity.UserTask;
import ru.tusur.edu.repository.task.TaskRepository;
import ru.tusur.edu.repository.task.UserTaskRepository;
import ru.tusur.edu.security.entity.User;
import ru.tusur.edu.security.repository.UserRepository;
import ru.tusur.edu.security.service.UserService;
import ru.tusur.edu.type.task.TaskDifficultyType;

import java.sql.Timestamp;

import static ru.tusur.edu.util.UserUtil.getUserId;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;

    @SneakyThrows
    @Transactional
    public void checkIfUserStartedTask(Long taskId) {
        Long userId = getUserId();
        if (!userTaskRepository.existsByUserIdAndTaskId(userId, taskId)) {
            initializeUserTask(userId, taskId);
        }
    }

    @SneakyThrows
    private void initializeUserTask(Long userId, Long taskId) {
        UserTask newUserTask = UserTask.builder()
                .user(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId)))
                .task(taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId)))
                .completeDate(null)
                .build();

        userTaskRepository.save(newUserTask);
    }

    @SneakyThrows
    @Transactional
    public void handleCorrectSolution(Task task, Long userId) {
        UserTask userTask = userTaskRepository.findByUserIdAndTaskId(userId, task.getId())
                .orElseThrow(() -> new RuntimeException("User has not started this task yet"));

        if (userTask.getIsFinished()) {
            return;
        }

        userTask.setCompleteDate(new Timestamp(System.currentTimeMillis()));
        userTask.setIsFinished(true);
        userTaskRepository.save(userTask);

        TaskDifficultyType difficultyType = userTask.getTask().getTaskDifficulty().getName();

        User user = userTask.getUser();
        userService.addBalance(user, difficultyType);
        userService.addActivity(user, difficultyType);
        userRepository.save(user);
    }


    public Long countSolvedTasksByUserIdAndTheme(Long userId, TaskTheme theme) {
        return userTaskRepository.countByUserIdAndTaskTheme(userId, theme);
    }
}
