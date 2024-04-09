package ru.tusur.edu.web.packet.request;

import lombok.Data;

import java.util.Map;

@Data
public class TestSolutionRequest {

    private Map<Long, String> solutions;
}
