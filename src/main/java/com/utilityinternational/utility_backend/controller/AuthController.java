package com.utilityinternational.utility_backend.controller;

import com.utilityinternational.utility_backend.dto.request.LoginRequest;
import com.utilityinternational.utility_backend.dto.request.RegisterRequest;
import com.utilityinternational.utility_backend.dto.response.JwtResponse;
import com.utilityinternational.utility_backend.dto.response.MessageResponse;
import com.utilityinternational.utility_backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Login, register and token refresh endpoints")
public class AuthController {
 
    private final AuthService authService;
 
    @PostMapping("/login")
    @Operation(summary = "Authenticate user and return JWT tokens")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
 
    @PostMapping("/register")
    @Operation(summary = "Register a new user (Customer or Call Center Agent)")
    public ResponseEntity<MessageResponse> register(
            @Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }
 
    @PostMapping("/refresh")
    @Operation(summary = "Exchange a valid refresh token for new tokens")
    public ResponseEntity<JwtResponse> refresh(
            @RequestParam String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
}