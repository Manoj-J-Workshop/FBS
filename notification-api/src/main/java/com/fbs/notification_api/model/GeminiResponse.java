package com.fbs.notification_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Model class representing Gemini API response
 */
public class GeminiResponse {
    
    @JsonProperty("candidates")
    private List<GeminiCandidate> candidates;
    
    public GeminiResponse() {}
    
    public GeminiResponse(List<GeminiCandidate> candidates) {
        this.candidates = candidates;
    }
    
    public List<GeminiCandidate> getCandidates() {
        return candidates;
    }
    
    public void setCandidates(List<GeminiCandidate> candidates) {
        this.candidates = candidates;
    }
    
    @Override
    public String toString() {
        return "GeminiResponse{" +
                "candidates=" + candidates +
                '}';
    }
}
