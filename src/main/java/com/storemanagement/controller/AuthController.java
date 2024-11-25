package com.storemanagement.controller;

import com.storemanagement.model.AuthResponse;
import com.storemanagement.model.Role;
import com.storemanagement.model.User;
import com.storemanagement.model.UserCreationDto;
import com.storemanagement.repository.UserRepository;
import com.storemanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.storemanagement.util.JwtUtil;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
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

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserCreationDto userCreationDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userCreationDto.getUsername(),
                        userCreationDto.getPassword()
                )
        );

        UserDetails userDetails = userService.loadUserByUsername(userCreationDto.getUsername());
        String jwt = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}

