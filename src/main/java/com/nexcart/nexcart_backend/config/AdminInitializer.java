package com.nexcart.nexcart_backend.config;

import com.nexcart.nexcart_backend.entity.Role;
import com.nexcart.nexcart_backend.entity.User;
import com.nexcart.nexcart_backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminInitializer implements CommandLineRunner {
    @Value("${nexcart.admin.email}")
    private String adminEmail;
    @Value("${nexcart.admin.password}")
    private String adminPassword;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    public AdminInitializer(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @Override
    public void run(String... args) {
        Optional<User> adminUser = userRepo.findByEmail(adminEmail);
        if (adminUser.isEmpty()) {
            User admin = User.builder().
                    email(adminEmail).
                    password(passwordEncoder.encode(adminPassword)).
                    role(Role.ADMIN).
                    build();
            userRepo.save(admin);
        }
    }
}
