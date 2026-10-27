package com.reduxr.mapper;

import com.reduxr.config.MapperConfig;
import com.reduxr.dto.UserRegistrationRequestDto;
import com.reduxr.dto.UserRegistrationResponseDto;
import com.reduxr.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserRegistrationResponseDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toModel(UserRegistrationRequestDto requestDto);
}
