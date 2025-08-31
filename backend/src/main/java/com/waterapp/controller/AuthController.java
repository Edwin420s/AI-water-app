package com.waterapp.controller;

import com.waterapp.dto.AuthResponse;
import com.waterapp.dto.LoginRequest;
import com.waterapp.dto.SignupRequest;
import com.waterapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    /**
     * User registration endpoint
     * @param signupRequest signup request data
     * @return ResponseEntity with authentication response
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        AuthResponse response = userService.registerUser(signupRequest);
        
        if (response.getToken() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * User login endpoint
     * @param loginRequest login request data
     * @return ResponseEntity with authentication response
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse response = userService.authenticateUser(loginRequest);
        
        if (response.getToken() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Health check endpoint
     * @return ResponseEntity with health status
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Water Reservoir Auth Service is running!");
    }
}
