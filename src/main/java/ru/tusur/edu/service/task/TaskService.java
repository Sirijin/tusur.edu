package ru.tusur.edu.service.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tusur.edu.repository.task.TaskRepository;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
}
