package com.sweetshop.sweetshop_backend.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sweetshop.sweetshop_backend.model.User;
import com.sweetshop.sweetshop_backend.repository.UserRepository;
import com.sweetshop.sweetshop_backend.util.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return jwtUtil.generateToken(username);
            }
        }
        
        return null; // Return null for invalid credentials
    }

    public String registerUser(String username, String password) {
        // Check if user already exists
        if (userRepository.existsByUsername(username)) {
            return "Username already exists";
        }
        
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            return "Username cannot be empty";
        }
        
        if (password == null || password.length() < 6) {
            return "Password must be at least 6 characters long";
        }
        
        // Create new user
        User newUser = new User();
        newUser.setUsername(username.trim());
        newUser.setPassword(passwordEncoder.encode(password));
        
        try {
            userRepository.save(newUser);
            return "User registered successfully";
        } catch (Exception e) {
            return "Registration failed";
        }
    }
}