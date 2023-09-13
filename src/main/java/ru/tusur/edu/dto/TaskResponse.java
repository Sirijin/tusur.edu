package ru.tusur.edu.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class TaskResponse  {

    @NotNull
    private List<TaskDto> tasks;

    @NotNull
    private long total;
}
