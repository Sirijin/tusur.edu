package ru.tusur.edu.dto.achievement;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder(toBuilder = true)
public record AchievementResponse(@NonNull List<AchievementDto> achievements, long total) {
}
