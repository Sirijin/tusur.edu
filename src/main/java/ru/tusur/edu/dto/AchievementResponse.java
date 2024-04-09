package ru.tusur.edu.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class AchievementResponse {

    @NotEmpty
    private List<AchievementDto> items;
    private long total;
}
