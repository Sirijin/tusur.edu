package ru.tusur.edu.dto.task;


import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder(toBuilder = true)
public record TaskResponse(@NonNull List<TaskDto> tasks, long total) {
}
