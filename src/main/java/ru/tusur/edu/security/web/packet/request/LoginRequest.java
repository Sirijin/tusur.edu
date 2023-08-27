package ru.tusur.edu.security.web.packet.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;
}
