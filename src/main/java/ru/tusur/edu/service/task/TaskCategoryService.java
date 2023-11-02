package ru.tusur.edu.service.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.edu.repository.task.TaskDifficultyRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskDifficultyService {

    private final TaskDifficultyRepository taskDifficultyRepository;


}
