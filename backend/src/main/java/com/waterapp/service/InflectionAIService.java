package com.waterapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waterapp.config.InflectionAIConfig;
import com.waterapp.dto.AIInsightRequest;
import com.waterapp.dto.AIInsightResponse;
import com.waterapp.dto.WaterReservoirDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class InflectionAIService {
    
    @Autowired
    private InflectionAIConfig aiConfig;
    
    @Autowired
    private WaterReservoirService waterReservoirService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * Get AI insights based on user query
     */
    public AIInsightResponse getAIInsight(AIInsightRequest request) {
        try {
            String systemPrompt = buildSystemPrompt(request.getAnalysisType());
            String userPrompt = buildUserPrompt(request);
            
            String response = callInflectionAPI(systemPrompt, userPrompt);
            return parseAIResponse(response, request.getAnalysisType());
            
        } catch (Exception e) {
            return new AIInsightResponse("Error getting AI insight: " + e.getMessage());
        }
    }
    
    /**
     * Get water level predictions for a specific reservoir
     */
    public AIInsightResponse predictWaterLevels(Long reservoirId, int daysAhead) {
        try {
            var reservoir = waterReservoirService.getReservoirById(reservoirId);
            if (reservoir.isEmpty()) {
                return new AIInsightResponse("Reservoir not found");
            }
            
            WaterReservoirDto reservoirData = reservoir.get();
            String context = buildReservoirContext(reservoirData);
            
            String systemPrompt = "You are a water management expert specializing in reservoir level predictions for Kenya. " +
                    "Provide accurate predictions based on current data, seasonal patterns, and consumption trends.";
            
            String userPrompt = String.format(
                    "Based on the following reservoir data, predict water levels for the next %d days:\n%s\n" +
                    "Consider seasonal rainfall patterns in Kenya, current consumption rates, and provide specific predictions.",
                    daysAhead, context
            );
            
            String response = callInflectionAPI(systemPrompt, userPrompt);
            AIInsightResponse aiResponse = parseAIResponse(response, "PREDICTION");
            aiResponse.setConfidence("MEDIUM");
            
            return aiResponse;
            
        } catch (Exception e) {
            return new AIInsightResponse("Error predicting water levels: " + e.getMessage());
        }
    }
    
    /**
     * Get management recommendations for critical reservoirs
     */
    public AIInsightResponse getCriticalReservoirRecommendations() {
        try {
            List<WaterReservoirDto> criticalReservoirs = waterReservoirService.getCriticalReservoirs();
            
            if (criticalReservoirs.isEmpty()) {
                AIInsightResponse response = new AIInsightResponse(
                    "Great news! No reservoirs are currently in critical condition.", 
                    "RECOMMENDATION"
                );
                response.setConfidence("HIGH");
                return response;
            }
            
            String context = buildCriticalReservoirsContext(criticalReservoirs);
            
            String systemPrompt = "You are a water crisis management expert for Kenya. " +
                    "Provide actionable recommendations for managing critical water reservoir situations.";
            
            String userPrompt = "The following reservoirs are in critical condition (below 40% capacity):\n" +
                    context + "\n" +
                    "Provide immediate action recommendations, resource allocation suggestions, and emergency measures.";
            
            String response = callInflectionAPI(systemPrompt, userPrompt);
            AIInsightResponse aiResponse = parseAIResponse(response, "RECOMMENDATION");
            aiResponse.setConfidence("HIGH");
            
            return aiResponse;
            
        } catch (Exception e) {
            return new AIInsightResponse("Error getting recommendations: " + e.getMessage());
        }
    }
    
    /**
     * Call the Inflection AI API
     */
    private String callInflectionAPI(String systemPrompt, String userPrompt) throws Exception {
        URL url = new URL(aiConfig.getEndpoint());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + aiConfig.getKey());
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setConnectTimeout(aiConfig.getTimeout());
        conn.setReadTimeout(aiConfig.getTimeout());
        
        // Build JSON request body
        String requestBody = String.format("""
            {
              "model": "%s",
              "messages": [
                {"role": "system", "content": "%s"},
                {"role": "user", "content": "%s"}
              ],
              "max_tokens": 1000,
              "temperature": 0.7
            }
            """, aiConfig.getModel(), 
            systemPrompt.replace("\"", "\\\"").replace("\n", "\\n"),
            userPrompt.replace("\"", "\\\"").replace("\n", "\\n"));
        
        // Send request
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        
        // Read response
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }
        }
        
        return response.toString();
    }
    
    /**
     * Parse AI response and extract content
     */
    private AIInsightResponse parseAIResponse(String jsonResponse, String analysisType) {
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode choices = root.get("choices");
            
            if (choices != null && choices.isArray() && choices.size() > 0) {
                JsonNode message = choices.get(0).get("message");
                if (message != null) {
                    String content = message.get("content").asText();
                    
                    AIInsightResponse response = new AIInsightResponse(content, analysisType);
                    
                    // Extract recommendations if present
                    if (content.toLowerCase().contains("recommend")) {
                        response.setRecommendations(extractRecommendations(content));
                    }
                    
                    return response;
                }
            }
            
            return new AIInsightResponse("Unable to parse AI response");
            
        } catch (Exception e) {
            return new AIInsightResponse("Error parsing AI response: " + e.getMessage());
        }
    }
    
    /**
     * Build system prompt based on analysis type
     */
    private String buildSystemPrompt(String analysisType) {
        return switch (analysisType != null ? analysisType.toUpperCase() : "GENERAL") {
            case "PREDICTION" -> "You are a water resource prediction expert for Kenya. Provide accurate forecasts based on data analysis, seasonal patterns, and consumption trends.";
            case "RECOMMENDATION" -> "You are a water management consultant for Kenya. Provide actionable recommendations for reservoir management and water conservation.";
            case "ANALYSIS" -> "You are a water data analyst for Kenya. Provide detailed analysis of water reservoir conditions and trends.";
            default -> "You are a helpful water management assistant for Kenya's water reservoir system. Provide informative and accurate responses about water management.";
        };
    }
    
    /**
     * Build user prompt with context
     */
    private String buildUserPrompt(AIInsightRequest request) {
        StringBuilder prompt = new StringBuilder(request.getQuery());
        
        if (request.getContext() != null && !request.getContext().trim().isEmpty()) {
            prompt.append("\n\nContext: ").append(request.getContext());
        }
        
        // Add general reservoir statistics as context
        try {
            var stats = waterReservoirService.getReservoirStatistics();
            prompt.append("\n\nCurrent System Overview:")
                  .append("\n- Total Reservoirs: ").append(stats.getTotalReservoirs())
                  .append("\n- Critical Reservoirs: ").append(stats.getCriticalReservoirs())
                  .append("\n- Average Water Level: ").append(stats.getAverageWaterLevel()).append("%");
        } catch (Exception e) {
            // Continue without stats if there's an error
        }
        
        return prompt.toString();
    }
    
    /**
     * Build context for a specific reservoir
     */
    private String buildReservoirContext(WaterReservoirDto reservoir) {
        return String.format("""
            Reservoir: %s
            Location: %s, %s, %s
            Current Level: %.2f%% (%.2f/%.2f cubic meters)
            Status: %s
            Last Updated: %s
            """,
            reservoir.getName(),
            reservoir.getWard(), reservoir.getSubCounty(), reservoir.getCounty(),
            reservoir.getCurrentLevelPercentage(),
            reservoir.getCurrentLevelM3(), reservoir.getTotalCapacityM3(),
            reservoir.getStatus(),
            reservoir.getLastUpdated()
        );
    }
    
    /**
     * Build context for critical reservoirs
     */
    private String buildCriticalReservoirsContext(List<WaterReservoirDto> reservoirs) {
        StringBuilder context = new StringBuilder();
        for (WaterReservoirDto reservoir : reservoirs) {
            context.append("- ").append(reservoir.getName())
                   .append(" (").append(reservoir.getCounty()).append("): ")
                   .append(String.format("%.1f%%", reservoir.getCurrentLevelPercentage()))
                   .append(" capacity remaining\n");
        }
        return context.toString();
    }
    
    /**
     * Extract recommendations from AI response
     */
    private List<String> extractRecommendations(String content) {
        // Simple extraction - look for numbered or bulleted lists
        return Arrays.stream(content.split("\n"))
                .filter(line -> line.matches("^\\s*[\\d\\-\\*•].*"))
                .map(String::trim)
                .toList();
    }
}
