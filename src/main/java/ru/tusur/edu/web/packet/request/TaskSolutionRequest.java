package ru.tusur.edu.web.packet.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "test description")
public class TaskSolutionRequest {

    @Schema(description = "test description", example = "254")
    private List<String> solution;
}
