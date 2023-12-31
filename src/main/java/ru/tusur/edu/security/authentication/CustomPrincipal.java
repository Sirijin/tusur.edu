package ru.tusur.edu.security.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.Principal;

@Data
@AllArgsConstructor
public class CustomPrincipal implements Principal {

    private Long id;
    private String name;
}
