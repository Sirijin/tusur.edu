package ru.tusur.edu.security.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tusur.edu.security.service.UserService;
import ru.tusur.edu.security.web.packet.dto.UserDto;
import ru.tusur.edu.web.packet.request.PageableRequest;

@RestController()
@RequestMapping(value = "/api/v1/admin", produces = {"application/json"})
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> findAllPageable(@Valid PageableRequest pageableRequest) {
        return ResponseEntity.ok(userService.findAllPageable(pageableRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findOneDto(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.save(dto));
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

}
