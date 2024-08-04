package com.example.RegistrationLogin.Repositories;

import com.example.RegistrationLogin.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> findById(Integer userId);
}
