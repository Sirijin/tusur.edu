package ru.tusur.edu.security.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tusur.edu.security.entity.Role;
import ru.tusur.edu.security.entity.User;
import ru.tusur.edu.security.type.RoleSet;
import ru.tusur.edu.security.web.packet.dto.UserDto;
import ru.tusur.edu.security.web.packet.request.RegisterRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMapper {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final RoleMapper roleMapper;

    public Optional<User> map(UserDto dto, String encodedPassword) {

        Role role = dto.getRole() == null ? roleMapper.map(RoleSet.USER_ROLE) : roleMapper.map(dto.getRole());

        return Optional.of(User
                .builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(encodedPassword)
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .lastName(dto.getLastName())
                .birthday(LocalDate.parse(dto.getBirthday(), formatter))
                .role(role)
                .build());
    }

    public Optional<UserDto> map(User entity) {
        return Optional.of(UserDto
                .builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .firstName(entity.getFirstName())
                .middleName(entity.getMiddleName())
                .lastName(entity.getLastName())
                .birthday(entity.getBirthday().format(formatter))
                .balance(entity.getBalance())
                .dailyActivity(entity.getDailyActivity())
                .daysInARow(entity.getDaysInARow())
                .role(roleMapper.map(entity.getRole()))
                .build());
    }

    public Optional<User> map(RegisterRequest request, String encodedPassword, Role role) {
        return Optional.of(User
                .builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(encodedPassword)
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .birthday(LocalDate.parse(request.getBirthday(), formatter))
                .balance(0)
                .dailyActivity(0.0)
                .daysInARow(0)
                .role(role)
                .build());
    }
}
