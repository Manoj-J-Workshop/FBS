package com.fbs.notification_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Root model class representing the Gemini API request structure
 */
public class GeminiRequest {
    
    @JsonProperty("contents")
    private List<GeminiContent> contents;
    
    public GeminiRequest() {}
    
    public GeminiRequest(List<GeminiContent> contents) {
        this.contents = contents;
    }
    
    public List<GeminiContent> getContents() {
        return contents;
    }
    
    public void setContents(List<GeminiContent> contents) {
        this.contents = contents;
    }
    
    @Override
    public String toString() {
        return "GeminiRequest{" +
                "contents=" + contents +
                '}';
    }
}
