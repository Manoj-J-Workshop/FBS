package com.fbs.notification_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class representing a Gemini part with text content
 */
public class GeminiPart {
    
    @JsonProperty("text")
    private String text;
    
    public GeminiPart() {}
    
    public GeminiPart(String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    @Override
    public String toString() {
        return "GeminiPart{" +
                "text='" + text + '\'' +
                '}';
    }
}
