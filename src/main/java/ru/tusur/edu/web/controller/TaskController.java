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
import ru.tusur.edu.annotation.PageableRequestAnnotation;
import ru.tusur.edu.dto.TaskDto;
import ru.tusur.edu.dto.TaskResponse;
import ru.tusur.edu.service.task.TaskService;
import ru.tusur.edu.web.packet.request.PageableRequest;
import ru.tusur.edu.web.packet.request.TaskSolutionRequest;
import ru.tusur.edu.web.packet.request.TestSolutionRequest;

import java.util.List;

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
    @PageableRequestAnnotation
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
        return ResponseEntity.ok(taskService.findOneDto(id));
    }

    @Operation(summary = "Add new task", description = "Add new task", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema)),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
    })
    @PostMapping
    public ResponseEntity<?> save(@RequestBody TaskDto dto) {
        return ResponseEntity.ok(taskService.save(dto));
    }

    @Operation(summary = "Add new task", description = "Add new task", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema)),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
    })
    @PostMapping("/save-list")
    public ResponseEntity<?> saveList(@RequestBody List<TaskDto> dtos) {
        return ResponseEntity.ok(taskService.saveList(dtos));
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
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Send TaskSolutionRequest on task by id", description = "Get tasks list and total count", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema)),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
    })
    @PostMapping("/{id}")
    public ResponseEntity<?> sendSolutionOnTaskById(@PathVariable("id") Long id, @RequestBody TaskSolutionRequest solution) {
        return ResponseEntity.ok(taskService.processUserSolution(id, solution));
    }

    @Operation(summary = "Generate test", description = "Generate test", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema)),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
    })
    @GetMapping("/test")
    public ResponseEntity<?> generateChallengingTest(@RequestParam int tasksAmount) {
        return ResponseEntity.ok(taskService.generateChallengingTest(tasksAmount));
    }

    @Operation(summary = "Submit test", description = "Submit test", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema)),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
    })
    @PostMapping("/test")
    public ResponseEntity<?> submitTest(@RequestBody TestSolutionRequest testSolutionRequest) {
        return ResponseEntity.ok(taskService.submitTest(testSolutionRequest));
    }
}
