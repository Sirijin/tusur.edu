package ru.tusur.edu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TaskDto {

    @PositiveOrZero
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String taskThemeType;

    @NotBlank
    private String taskDifficultyType;

    @NotBlank
    private String taskLevelType;

    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<String> taskSolutions;
}
