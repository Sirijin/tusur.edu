package ru.tusur.edu.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.tusur.edu.security.entity.User;
import ru.tusur.edu.security.web.packet.dto.UserDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewUserMapper {


    User toEntity(UserDto dto);

    UserDto toDto(User user);


}
