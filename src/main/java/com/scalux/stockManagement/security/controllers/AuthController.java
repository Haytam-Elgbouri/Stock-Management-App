package com.scalux.stockManagement.security.controllers;


import com.scalux.stockManagement.security.models.dtos.JwtResponseDto;
import com.scalux.stockManagement.security.models.dtos.LoginRequestDto;
import com.scalux.stockManagement.security.services.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginRequestDto dto) {
        return new ResponseEntity<>(authService.login(dto), HttpStatus.OK);
    }

}
