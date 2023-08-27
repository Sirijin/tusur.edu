package ru.tusur.edu.security.web.packet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tusur.edu.security.type.RoleSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserDto {

    private Long id;
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String birthday;
    private Integer balance;
    private Double dailyActivity;
    private Integer daysInARow;
    private RoleSet role;
}
