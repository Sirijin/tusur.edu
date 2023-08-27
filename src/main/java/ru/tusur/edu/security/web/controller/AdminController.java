package ru.tusur.edu.security.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tusur.edu.security.service.UserService;
import ru.tusur.edu.security.web.packet.dto.UserDto;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.saveUser(dto));
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable("id") Long id, @RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
