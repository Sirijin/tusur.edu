package ru.tusur.edu.security.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.tusur.edu.security.entity.Role;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum RoleSet {
    ADMIN_ROLE("ADMIN_ROLE"),
    USER_ROLE("USER_ROLE");

    private String name;

    public static Optional<RoleSet> findByName(Role role) {
        if (role == null || role.getName() == null) {
            return Optional.empty();
        }

        for (RoleSet roleset : RoleSet.values()) {
            if (roleset.equals(role.getName())) {
                return Optional.of(roleset);
            }
        }
        return Optional.empty();
    }
}
