package ru.tusur.edu.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tusur.edu.security.entity.Role;
import ru.tusur.edu.security.type.RoleSet;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleSet name);
}