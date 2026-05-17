package com.utilityinternational.utility_backend.config;

import com.utilityinternational.utility_backend.entity.Role;
import com.utilityinternational.utility_backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
 
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
 
    private final RoleRepository roleRepository;
 
    @Override
    public void run(String... args) {
        seedRole(Role.ERole.ROLE_CUSTOMER);
        seedRole(Role.ERole.ROLE_CALL_CENTER_AGENT);
        log.info("Roles initialized successfully.");
    }
 
    private void seedRole(Role.ERole eRole) {
        if (roleRepository.findByName(eRole).isEmpty()) {
            Role role = new Role();
            role.setName(eRole);
            roleRepository.save(role);
            log.info("Seeded role: {}", eRole.name());
        }
    }
}