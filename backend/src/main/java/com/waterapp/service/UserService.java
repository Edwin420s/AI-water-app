package com.waterapp.service;

import com.waterapp.dto.AuthResponse;
import com.waterapp.dto.LoginRequest;
import com.waterapp.dto.SignupRequest;
import com.waterapp.dto.UserDto;
import com.waterapp.entity.User;
import com.waterapp.repository.UserRepository;
import com.waterapp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * Register a new user
     * @param signupRequest signup request data
     * @return AuthResponse with JWT token and user info
     */
    public AuthResponse registerUser(SignupRequest signupRequest) {
        try {
            // Validate password confirmation
            if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
                return new AuthResponse("Passwords do not match");
            }
            
            // Check if user already exists
            if (userRepository.existsByEmail(signupRequest.getEmail())) {
                return new AuthResponse("User with this email already exists");
            }
            
            // Create new user
            User user = new User();
            user.setFullName(signupRequest.getFullName());
            user.setEmail(signupRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            user.setCreatedAt(LocalDateTime.now());
            user.setRole(User.UserRole.USER);
            
            // Save user
            User savedUser = userRepository.save(user);
            
            // Generate JWT token
            String token = jwtUtil.generateToken(savedUser.getEmail());
            
            // Return response
            return new AuthResponse(token, new UserDto(savedUser));
            
        } catch (Exception e) {
            return new AuthResponse("Registration failed: " + e.getMessage());
        }
    }
    
    /**
     * Authenticate user login
     * @param loginRequest login request data
     * @return AuthResponse with JWT token and user info
     */
    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        try {
            // Find user by email
            Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
            
            if (userOpt.isEmpty()) {
                return new AuthResponse("Invalid email or password");
            }
            
            User user = userOpt.get();
            
            // Verify password
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return new AuthResponse("Invalid email or password");
            }
            
            // Update last login
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
            
            // Generate JWT token
            String token = jwtUtil.generateToken(user.getEmail());
            
            // Return response
            return new AuthResponse(token, new UserDto(user));
            
        } catch (Exception e) {
            return new AuthResponse("Authentication failed: " + e.getMessage());
        }
    }
    
    /**
     * Get user by email
     * @param email user's email
     * @return Optional containing user if found
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Get user by ID
     * @param id user's ID
     * @return Optional containing user if found
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    /**
     * Update user's last login time
     * @param email user's email
     */
    public void updateLastLogin(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
        }
    }
}
