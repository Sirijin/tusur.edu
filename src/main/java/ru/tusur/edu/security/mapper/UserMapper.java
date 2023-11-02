package ru.tusur.edu.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.tusur.edu.security.entity.Role;
import ru.tusur.edu.security.entity.User;
import ru.tusur.edu.security.type.RoleSet;
import ru.tusur.edu.security.web.packet.dto.UserDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(UserDto dto);

    @Mapping(target = "role", expression = "java(roleToRoleSet(user.getRole()))")
    UserDto toDto(User user);

    default RoleSet roleToRoleSet(Role role) {
        return role.getName();
    }
}
