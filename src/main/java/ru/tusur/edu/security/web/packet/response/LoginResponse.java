package ru.tusur.edu.security.web.packet.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class LoginResponse {

    private String token;
}
