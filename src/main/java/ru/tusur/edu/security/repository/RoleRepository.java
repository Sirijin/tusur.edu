package ru.tusur.edu.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.tusur.edu.security.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}