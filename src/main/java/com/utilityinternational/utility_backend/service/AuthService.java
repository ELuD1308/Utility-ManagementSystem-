package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.dto.request.LoginRequest;
import com.utilityinternational.utility_backend.dto.request.RegisterRequest;
import com.utilityinternational.utility_backend.dto.response.JwtResponse;
import com.utilityinternational.utility_backend.dto.response.MessageResponse;
 
public interface AuthService {
 
    JwtResponse login(LoginRequest loginRequest);
 
    MessageResponse register(RegisterRequest registerRequest);
 
    JwtResponse refreshToken(String refreshToken);
}