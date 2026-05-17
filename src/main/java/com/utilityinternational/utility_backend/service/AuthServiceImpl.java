package com.utilityinternational.utility_backend.service;

import com.utilityinternational.utility_backend.dto.request.LoginRequest;
import com.utilityinternational.utility_backend.dto.request.RegisterRequest;
import com.utilityinternational.utility_backend.dto.response.JwtResponse;
import com.utilityinternational.utility_backend.dto.response.MessageResponse;
import com.utilityinternational.utility_backend.entity.Role;
import com.utilityinternational.utility_backend.entity.User;
import com.utilityinternational.utility_backend.exception.ResourceNotFoundException;
import com.utilityinternational.utility_backend.exception.TokenRefreshException;
import com.utilityinternational.utility_backend.repository.RoleRepository;
import com.utilityinternational.utility_backend.repository.UserRepository;
import com.utilityinternational.utility_backend.security.jwt.JwtUtils;
import com.utilityinternational.utility_backend.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
 
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
 
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
 
    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
 
        SecurityContextHolder.getContext().setAuthentication(authentication);
 
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String accessToken = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails.getUsername());
 
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
 
        return new JwtResponse(
                accessToken,
                refreshToken,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        );
    }
 
    @Override
    @Transactional
    public MessageResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException(
                    "Username '" + request.getUsername() + "' is already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "Email '" + request.getEmail() + "' is already in use");
        }
 
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .build();
 
        Set<String> requestedRoles = request.getRoles();
        Set<Role> roles = new HashSet<>();
 
        if (requestedRoles == null || requestedRoles.isEmpty()) {
            roles.add(getRole(Role.ERole.ROLE_CUSTOMER));
        } else {
            requestedRoles.forEach(roleName -> {
                switch (roleName.toUpperCase()) {
                    case "ROLE_CALL_CENTER_AGENT" ->
                            roles.add(getRole(Role.ERole.ROLE_CALL_CENTER_AGENT));
                    default ->
                            roles.add(getRole(Role.ERole.ROLE_CUSTOMER));
                }
            });
        }
 
        user.setRoles(roles);
        userRepository.save(user);
 
        return new MessageResponse("User registered successfully!");
    }
 
    @Override
    public JwtResponse refreshToken(String refreshToken) {
        if (!jwtUtils.validateJwtToken(refreshToken)) {
            throw new TokenRefreshException(refreshToken, "Refresh token is invalid or expired");
        }
 
        String username = jwtUtils.getUsernameFromJwtToken(refreshToken);
        userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
 
        String newAccessToken = jwtUtils.generateTokenFromUsername(username);
        String newRefreshToken = jwtUtils.generateRefreshToken(username);
 
        return JwtResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .tokenType("Bearer")
                .build();
    }
 
    private Role getRole(Role.ERole eRole) {
        return roleRepository.findByName(eRole)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Role", "name", eRole.name()));
    }
}