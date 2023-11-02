package ru.tusur.edu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.tusur.edu.dto.AchievementDto;
import ru.tusur.edu.entity.Achievement;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AchievementMapper {

    Achievement toEntity(AchievementDto dto);

    AchievementDto toDto(Achievement entity);
}
