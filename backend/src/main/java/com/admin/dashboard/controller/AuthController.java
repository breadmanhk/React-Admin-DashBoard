package com.admin.dashboard.controller;

import com.admin.dashboard.dto.AuthResponse;
import com.admin.dashboard.dto.LoginRequest;
import com.admin.dashboard.entity.User;
import com.admin.dashboard.service.UserService;
import com.admin.dashboard.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<User> userOpt = userService.getUserByEmail(loginRequest.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "User not found!"));
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid credentials!"));
        }

        if (user.getStatus() != User.Status.ACTIVE) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Account is inactive!"));
        }

        String jwt = jwtUtil.generateJwtToken(user.getEmail());

        return ResponseEntity.ok(new AuthResponse(jwt, user.getId(), user.getName(), user.getEmail(), user.getRole()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append(". ");
            }
            return ResponseEntity.badRequest()
                    .body(Map.of("message", errorMessage.toString().trim()));
        }

        try {
            User createdUser = userService.createUser(user);
            String jwt = jwtUtil.generateJwtToken(createdUser.getEmail());

            return ResponseEntity.ok(new AuthResponse(jwt, createdUser.getId(),
                    createdUser.getName(), createdUser.getEmail(), createdUser.getRole()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
}