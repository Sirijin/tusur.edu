package ru.tusur.edu.security.authentication;

import lombok.Builder;
import lombok.Data;
import ru.tusur.edu.security.entity.Role;

import java.util.Date;

@Data
@Builder(toBuilder = true)
public class TokenInfo {

    private Long id;
    private String username;
    private Role role;
    private Date startDate;
    private Date expiresAt;
}
