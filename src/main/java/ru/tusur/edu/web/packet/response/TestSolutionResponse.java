package ru.tusur.edu.web.packet.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestSolutionResponse {

    private Integer correctSolutionsCount;
}
