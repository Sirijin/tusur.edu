package ru.tusur.edu.security.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tusur.edu.security.entity.Role;
import ru.tusur.edu.security.service.RoleService;
import ru.tusur.edu.security.type.RoleSet;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final RoleService roleService;

    public Role map(RoleSet roleSet) {
        return roleService.findByName(roleSet).orElseThrow(() -> new RuntimeException("No such role found"));
    }

    public RoleSet map(Role role) {
        return RoleSet.findByName(role).orElseThrow(() -> new RuntimeException("No such role found"));
    }
}
