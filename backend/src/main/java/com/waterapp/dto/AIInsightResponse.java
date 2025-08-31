package com.waterapp.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AIInsightResponse {
    
    private String insight;
    private String analysisType;
    private List<String> recommendations;
    private String confidence; // HIGH, MEDIUM, LOW
    private LocalDateTime timestamp;
    private boolean success;
    private String errorMessage;
    
    // Default constructor
    public AIInsightResponse() {
        this.timestamp = LocalDateTime.now();
        this.success = true;
    }
    
    // Constructor for successful response
    public AIInsightResponse(String insight, String analysisType) {
        this();
        this.insight = insight;
        this.analysisType = analysisType;
    }
    
    // Constructor for error response
    public AIInsightResponse(String errorMessage) {
        this();
        this.success = false;
        this.errorMessage = errorMessage;
    }
    
    // Getters and Setters
    public String getInsight() {
        return insight;
    }
    
    public void setInsight(String insight) {
        this.insight = insight;
    }
    
    public String getAnalysisType() {
        return analysisType;
    }
    
    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }
    
    public List<String> getRecommendations() {
        return recommendations;
    }
    
    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }
    
    public String getConfidence() {
        return confidence;
    }
    
    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
