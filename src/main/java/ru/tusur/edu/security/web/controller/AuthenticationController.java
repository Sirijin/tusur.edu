package ru.tusur.edu.security.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.edu.security.service.UserService;
import ru.tusur.edu.security.web.request.LoginRequest;
import ru.tusur.edu.security.web.request.RegisterRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(RegisterRequest request) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(LoginRequest request) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo() {
        return ResponseEntity.ok().build();
    }
}
