package com.utilityinternational.utility_backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Utility Backend is running.";
    }

    @GetMapping("/api/health")
    public String health() {
        return "OK";
    }
}
