package ru.tusur.edu.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tusur.edu.dto.TaskDto;
import ru.tusur.edu.dto.TaskResponse;
import ru.tusur.edu.service.task.TaskService;
import ru.tusur.edu.web.packet.request.PageableRequest;
import ru.tusur.edu.web.packet.request.TaskSolutionRequest;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Get tasks list and total count", description = "Get tasks list and total count", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema)),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
    })
    @GetMapping("/list")
    public ResponseEntity<?> findAllPageable(@Valid PageableRequest pageableRequest) {
        return ResponseEntity.ok(taskService.findAllPageable(pageableRequest));
    }

    @Operation(summary = "Get task by id", description = "Get tasks list and total count", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema)),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(taskService.findOne(id));
    }

    @Operation(summary = "Get tasks list and total count by task category", description = "Get tasks list and total count", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema)),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
    })
    @GetMapping("/category/{category}")
    public ResponseEntity<?> findAllPageableByCategory(@PathVariable("category") String category,
                                                       @Valid PageableRequest pageableRequest) {
        return ResponseEntity.ok(taskService.findAllByCategory(category, pageableRequest));
    }

    @Operation(summary = "Get tasks list and total count by task difficulty", description = "Get tasks list and total count", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema)),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
    })
    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<?> findAllPageableByDifficulty(@PathVariable("difficulty") String difficulty,
                                                         @Valid PageableRequest pageableRequest) {
        return ResponseEntity.ok(taskService.findAllByDifficulty(difficulty, pageableRequest));
    }

    @Operation(summary = "Add new task", description = "Add new task", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema)),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
    })
    @PostMapping
    public ResponseEntity<?> add(@RequestBody TaskDto dto) {
        return ResponseEntity.ok(taskService.save(dto));
    }

    @Operation(summary = "Edit task", description = "Edit task", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema)),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody TaskDto dto) {
        return ResponseEntity.ok(taskService.update(id, dto));
    }

    @Operation(summary = "Delete task", description = "Delete task", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema)),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Send TaskSolutionRequest on task by id", description = "Get tasks list and total count", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema)),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
    })
    @PostMapping("/{id}")
    public ResponseEntity<?> sendSolutionOnTaskById(@PathVariable("id") Long id, @RequestBody TaskSolutionRequest solution) {
        return ResponseEntity.ok(taskService.sendSolution(id, solution));
    }
}
