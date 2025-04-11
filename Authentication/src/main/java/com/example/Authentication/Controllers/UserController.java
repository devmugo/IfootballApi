package com.example.Authentication.Controllers;

import com.example.Authentication.DTO.RegisterDTO;
import com.example.Authentication.Models.Users;
import com.example.Authentication.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody RegisterDTO user) {
        Users savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }
}

