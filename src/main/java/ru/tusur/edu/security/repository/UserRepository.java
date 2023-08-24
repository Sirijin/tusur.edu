package ru.tusur.edu.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tusur.edu.security.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}