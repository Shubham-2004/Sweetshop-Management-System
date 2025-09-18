package com.sweetshop.sweetshop_backend.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.sweetshop.sweetshop_backend.model.User;
import com.sweetshop.sweetshop_backend.repository.UserRepository;
import com.sweetshop.sweetshop_backend.util.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String loginUser(String username, String password) {
        try {
            if (username == null || password == null) {
                return null;
            }
            
            Optional<User> userOptional = userRepository.findByUsername(username);
            
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (password.equals(user.getPassword())) {
                    return jwtUtil.generateToken(username);
                }
            }
            
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public String registerUser(String username, String password) {
        try {
            if (userRepository.existsByUsername(username)) {
                return "Username already exists";
            }
            
            if (username == null || username.trim().isEmpty()) {
                return "Username cannot be empty";
            }
            
            if (password == null || password.length() < 6) {
                return "Password must be at least 6 characters long";
            }
            
            User newUser = new User();
            newUser.setUsername(username.trim());
            newUser.setPassword(password); // Store password as plain text
            
            userRepository.save(newUser);
            return "User registered successfully";
            
        } catch (Exception e) {
            return "Registration failed";
        }
    }
}