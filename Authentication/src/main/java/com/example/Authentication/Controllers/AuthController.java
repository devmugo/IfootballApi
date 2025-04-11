package com.example.Authentication.Controllers;

import com.example.Authentication.DTO.UserDTO;
import com.example.Authentication.Services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDTO loginRequest) {
        System.out.println(loginRequest.getEmail());
        String token = authService.login(loginRequest);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}

