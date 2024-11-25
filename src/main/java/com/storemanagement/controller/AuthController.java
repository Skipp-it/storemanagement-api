package com.storemanagement.controller;

import com.storemanagement.model.Role;
import com.storemanagement.model.User;
import com.storemanagement.model.UserCreationDto;
import com.storemanagement.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserCreationDto userCreationDto) {
        if (userRepository.findByUsername(userCreationDto.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        User user = new User();
        user.setUsername(userCreationDto.getUsername());
        user.setPassword(userCreationDto.getPassword());

        if (userCreationDto.getRole() != null) {
            try {
                Role role = Role.valueOf(userCreationDto.getRole().name());
                user.setRole(role);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid role specified");
            }
        } else {
            user.setRole(Role.ROLE_USER);
        }
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}

