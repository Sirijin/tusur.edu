package ru.tusur.edu.service.task;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.edu.entity.Task;
import ru.tusur.edu.entity.TaskSolution;
import ru.tusur.edu.repository.task.TaskSolutionRepository;
import ru.tusur.edu.web.packet.request.TaskSolutionRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskSolutionService {

    private final TaskSolutionRepository taskSolutionRepository;

    @SneakyThrows
    public void saveTaskSolutions(Task task, List<String> solutions) {
        taskSolutionRepository.saveAll(solutions.stream().map(s -> TaskSolution.builder().solution(s).task(task).build()).toList());
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    public boolean checkIfSolutionsAreCorrect(Task task, TaskSolutionRequest request) {
        List<String> correctAnswers = task.getTaskSolutions()
                .stream()
                .map(TaskSolution::getSolution)
                .toList();

        List<String> userAnswers = request.getSolution();

        return userAnswers.equals(correctAnswers);
    }
}
