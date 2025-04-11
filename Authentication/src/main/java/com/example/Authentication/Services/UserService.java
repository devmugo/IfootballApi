package com.example.Authentication.Services;

import com.example.Authentication.DTO.RegisterDTO;
import com.example.Authentication.Models.Users;
import com.example.Authentication.Repos.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Users registerUser(RegisterDTO userdto) {
        // Encrypt password before saving
        Users user = new Users();
        user.setFirstName(userdto.getFirstname());
        user.setLastName(userdto.getLastname());
        user.setRole(userdto.getRole());
        user.setEmail(userdto.getEmail());
        user.setPassword(passwordEncoder.encode(userdto.getEmail()));
        return userRepository.save(user);
    }
}

