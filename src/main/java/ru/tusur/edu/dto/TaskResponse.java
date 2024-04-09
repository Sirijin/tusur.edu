package ru.tusur.edu.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class TaskResponse {

    @NotEmpty
    private List<TaskDto> items;
    private long total;
}
