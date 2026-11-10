package com.scalux.stockManagement.security.mappers;

import com.scalux.stockManagement.security.models.dtos.CreateUserDto;
import com.scalux.stockManagement.security.models.dtos.UpdateUserDto;
import com.scalux.stockManagement.security.models.dtos.UserDto;
import com.scalux.stockManagement.security.models.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<User, UserDto> {
    User toEntity(UserDto userDto);
    User toEntity(CreateUserDto dto);
    User toEntity(UpdateUserDto dto);
    UserDto toDto(User user);
}
