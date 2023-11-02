package ru.tusur.edu.type.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TaskDifficultyType {
    DIFFICULTY_EASY(3, 3.0),
    DIFFICULTY_MEDIUM(5, 5.0),
    DIFFICULTY_HARD(7, 7.0),
    DIFFICULTY_IMPOSSIBLE(10, 10.0),
    DIFFICULTY_UNKNOWN(0, 0.0);

    private final int coinsReward;
    private final double activityReward;
}
