package ru.tusur.edu.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.edu.security.entity.Role;
import ru.tusur.edu.security.repository.RoleRepository;
import ru.tusur.edu.security.type.RoleSet;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;

    public Optional<Role> findByName(RoleSet roleSet) {
        return roleRepository.findByName(roleSet);
    }

    public Role map(RoleSet roleSet) {
        return findByName(roleSet).orElseThrow(() -> new RuntimeException("No such role found"));
    }

    public RoleSet map(Role role) {
        return RoleSet.findByName(role).orElseThrow(() -> new RuntimeException("No such role found"));
    }

}
