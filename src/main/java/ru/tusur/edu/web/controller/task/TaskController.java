package ru.tusur.edu.web.controller.task;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.edu.service.task.TaskService;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/list")
    public ResponseEntity<?> findTasks() {
        try {
            return ResponseEntity.ok(taskService.findAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTaskById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(taskService.findById(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
