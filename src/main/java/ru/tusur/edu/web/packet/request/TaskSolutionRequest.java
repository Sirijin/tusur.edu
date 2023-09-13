package ru.tusur.edu.web.packet.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "test description")
public class TaskSolutionRequest {

    @Schema(description = "test description", example = "254")
    private List<String> solution;
}
