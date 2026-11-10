package com.scalux.stockManagement.security.controllers;


import com.scalux.stockManagement.security.models.dtos.ChangePasswordDto;
import com.scalux.stockManagement.security.models.dtos.UserDto;
import com.scalux.stockManagement.security.services.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {
    private final IAuthService authService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile() {
        return new ResponseEntity<>(authService.getAuthenticatedUserProfile(), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @RequestBody @Valid ChangePasswordDto changePasswordDto
    ) {
        authService.changePassword(changePasswordDto.oldPassword(), changePasswordDto.newPassword());
        return ResponseEntity.noContent().build();
    }

}