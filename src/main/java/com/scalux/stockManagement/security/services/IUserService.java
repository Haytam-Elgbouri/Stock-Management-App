package com.scalux.stockManagement.security.services;


import com.scalux.stockManagement.security.models.dtos.ChangeUserStatus;
import com.scalux.stockManagement.security.models.dtos.CreateUserDto;
import com.scalux.stockManagement.security.models.dtos.UpdateUserDto;
import com.scalux.stockManagement.security.models.dtos.UserDto;
import com.scalux.stockManagement.security.models.entities.User;

public interface IUserService extends GenericService<CreateUserDto, UpdateUserDto, UserDto, Long> {
    void changeUserStatus(Long id, ChangeUserStatus changeUserStatus);
    User getUserByUsername(String username);
}
