package com.scalux.stockManagement.security.services;

import com.scalux.stockManagement.security.models.dtos.JwtResponseDto;
import com.scalux.stockManagement.security.models.dtos.LoginRequestDto;
import com.scalux.stockManagement.security.models.dtos.UserDto;

public interface IAuthService {
    JwtResponseDto login(LoginRequestDto dto);
    UserDto getAuthenticatedUserProfile();
    void changePassword(String oldPassword, String newPassword);

}
