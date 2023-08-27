package ru.tusur.edu.security.web.packet.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.tusur.edu.security.web.packet.dto.UserDto;

@Data
@AllArgsConstructor
public class RegisterResponse {

    private UserDto userDto;
}
