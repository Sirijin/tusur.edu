package ru.tusur.edu.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class AchievementResponse {

    @NotNull
    private List<AchievementDto> achievements;

    @NotNull
    private long total;
}
