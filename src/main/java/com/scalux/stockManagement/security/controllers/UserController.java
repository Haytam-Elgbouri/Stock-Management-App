package com.scalux.stockManagement.security.controllers;


import com.scalux.stockManagement.security.models.dtos.ChangeUserStatus;
import com.scalux.stockManagement.security.models.dtos.CreateUserDto;
import com.scalux.stockManagement.security.models.dtos.UpdateUserDto;
import com.scalux.stockManagement.security.models.dtos.UserDto;
import com.scalux.stockManagement.security.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final IUserService service;

    @PostMapping("/create-user")
    public ResponseEntity<UserDto> createUserInfo(@Valid @RequestBody CreateUserDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }


    @PutMapping("/update-user-information/{id}")
    public ResponseEntity<UserDto> updateUserInfo(
            @Valid @RequestBody UpdateUserDto updateUserDto,
            @PathVariable Long id) {
        return new ResponseEntity<>(service.update(updateUserDto, id), HttpStatus.OK);
    }

    @PutMapping("/change-user-status/{id}")
    public ResponseEntity<Map<String, String>> updateUserStatus(
            @Valid @RequestBody ChangeUserStatus changeUserStatus,
            @PathVariable Long id) {

        service.changeUserStatus(id, changeUserStatus);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User status changed successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/pages/users")
    public ResponseEntity<Page<UserDto>> getPagesUsers(int pageNumber, int size){
        return new ResponseEntity<>(service.findAll(pageNumber, size), HttpStatus.OK);
    }


    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        service.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User status deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

}