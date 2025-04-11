package com.example.Authentication.Init;

import com.example.Authentication.Enums.Roles;
import com.example.Authentication.Models.Users;
import com.example.Authentication.Repos.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createAdminIfNotExists() {
        if (!userRepository.existsByRole(Roles.SUPER_ADMIN)) {
            Users admin = Users.builder()
                    .firstName("Amos")
                    .lastName("Mwangi")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("ramo@123"))
                    .role(Roles.SUPER_ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("Admin user created successfully.");
        }
    }
}

