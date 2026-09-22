package com.nexcart.nexcart_backend.service;

import com.nexcart.nexcart_backend.dto.LoginRequest;
import com.nexcart.nexcart_backend.dto.RegisterRequest;
import com.nexcart.nexcart_backend.entity.Role;
import com.nexcart.nexcart_backend.entity.User;
import com.nexcart.nexcart_backend.exception.EmailAlreadyExistsException;
import com.nexcart.nexcart_backend.exception.InvalidCredentialsException;
import com.nexcart.nexcart_backend.repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthService(UserRepo userRepo,BCryptPasswordEncoder passwordEncoder,JwtService jwtService){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    public void register(RegisterRequest request){

        Optional<User> existingUser =
                userRepo.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .role(Role.USER)
                .build();

        userRepo.save(user);
    }

    public String login(LoginRequest request) {
Optional<User> user = userRepo.findByEmail(request.getEmail());
if (user.isEmpty()){
    throw new InvalidCredentialsException("Invalid Email or Password");
}
User existingUser = user.get();
        if (!passwordEncoder.matches(
                request.getPassword(),
                existingUser.getPassword())) {

            throw new InvalidCredentialsException("Invalid email or password");
        }
        return jwtService.generateToken(
                existingUser.getEmail()
        );
    }
}
