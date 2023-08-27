package ru.tusur.edu.web.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.edu.security.service.UserService;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/lk")
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserInfo(authentication));
    }
}
