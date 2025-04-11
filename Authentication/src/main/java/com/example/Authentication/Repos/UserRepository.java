package com.example.Authentication.Repos;

import com.example.Authentication.Enums.Roles;
import com.example.Authentication.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);
    Optional<Users> findByEmail(String email);
    boolean existsByRole(Roles role);
}
