package com.waterapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AIInsightRequest {
    
    @NotBlank(message = "Query is required")
    @Size(max = 1000, message = "Query must not exceed 1000 characters")
    private String query;
    
    private String context; // Optional context about reservoirs
    private String analysisType; // PREDICTION, RECOMMENDATION, ANALYSIS, GENERAL
    
    // Default constructor
    public AIInsightRequest() {}
    
    // Constructor with required fields
    public AIInsightRequest(String query) {
        this.query = query;
    }
    
    // Constructor with all fields
    public AIInsightRequest(String query, String context, String analysisType) {
        this.query = query;
        this.context = context;
        this.analysisType = analysisType;
    }
    
    // Getters and Setters
    public String getQuery() {
        return query;
    }
    
    public void setQuery(String query) {
        this.query = query;
    }
    
    public String getContext() {
        return context;
    }
    
    public void setContext(String context) {
        this.context = context;
    }
    
    public String getAnalysisType() {
        return analysisType;
    }
    
    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }
}
