package com.fbs.notification_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Model class representing Gemini content with parts
 */
public class GeminiContent {
    
    @JsonProperty("parts")
    private List<GeminiPart> parts;
    
    public GeminiContent() {}
    
    public GeminiContent(List<GeminiPart> parts) {
        this.parts = parts;
    }
    
    public List<GeminiPart> getParts() {
        return parts;
    }
    
    public void setParts(List<GeminiPart> parts) {
        this.parts = parts;
    }
    
    @Override
    public String toString() {
        return "GeminiContent{" +
                "parts=" + parts +
                '}';
    }
}
