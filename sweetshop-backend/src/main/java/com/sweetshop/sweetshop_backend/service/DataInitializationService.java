// filepath: c:\Users\hrall\Desktop\Sweetshop-Management-System\sweetshop-backend\src\main\java\com\sweetshop\sweetshop_backend\service\DataInitializationService.java
package com.sweetshop.sweetshop_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sweetshop.sweetshop_backend.model.User;
import com.sweetshop.sweetshop_backend.repository.UserRepository;

@Service
public class DataInitializationService implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create default admin user if it doesn't exist
        if (userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("123Shubham"));
            userRepository.save(adminUser);
            System.out.println("Default admin user created with username: admin");
        }
    }
}