package ru.tusur.edu.security.web.packet.request;

import lombok.Data;

@Data
public class RegisterRequest {

    private String email;
    private String username;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String birthday;
}
