package com.waterapp.dto;

public class AuthResponse {
    private String token;
    private String tokenType;
    private UserDto user;
    private String message;
    
    // Default constructor
    public AuthResponse() {}
    
    // Constructor with token and user
    public AuthResponse(String token, UserDto user) {
        this.token = token;
        this.tokenType = "Bearer";
        this.user = user;
        this.message = "Authentication successful";
    }
    
    // Constructor with message only (for errors)
    public AuthResponse(String message) {
        this.message = message;
    }
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getTokenType() {
        return tokenType;
    }
    
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    
    public UserDto getUser() {
        return user;
    }
    
    public void setUser(UserDto user) {
        this.user = user;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
