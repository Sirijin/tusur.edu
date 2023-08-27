package ru.tusur.edu.service.task;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.edu.dto.task.TaskDto;
import ru.tusur.edu.dto.task.TaskResponse;
import ru.tusur.edu.mapper.TaskMapper;
import ru.tusur.edu.repository.task.TaskRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @SneakyThrows
    public TaskResponse findAll() {
        return TaskResponse
                .builder()
                .tasks(taskRepository.findAll().stream().map(taskMapper::map).collect(Collectors.toList()))
                .total(taskRepository.count())
                .build();
    }

    @SneakyThrows
    public TaskDto findById(Long id) {
        return taskRepository.findById(id).map(taskMapper::map).orElse(null);
    }


}
