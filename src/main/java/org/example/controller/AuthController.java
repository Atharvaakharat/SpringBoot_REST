package org.example.controller;

import jakarta.validation.Valid;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
@Autowired
  private  AuthenticationManager authenticationManager;
  private  JwtTokenProvider jwtTokenProvider;
  private  UserRepository userRepository;
  private  PasswordEncoder passwordEncoder;

  public AuthController(AuthenticationManager authenticationManager,
                        JwtTokenProvider jwtTokenProvider,
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@Valid @RequestBody User user) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword()
            )
    );

    String token = jwtTokenProvider.generateToken(authentication);
    return ResponseEntity.ok(token);
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@Valid @RequestBody User user) {
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    if (user.getRole() == null || user.getRole().isBlank()) {
      user.setRole("ROLE_USER");
    }

    userRepository.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
  }
}

