package com.fbs.notification_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class representing a Gemini candidate response
 */
public class GeminiCandidate {
    
    @JsonProperty("content")
    private GeminiContent content;
    
    public GeminiCandidate() {}
    
    public GeminiCandidate(GeminiContent content) {
        this.content = content;
    }
    
    public GeminiContent getContent() {
        return content;
    }
    
    public void setContent(GeminiContent content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return "GeminiCandidate{" +
                "content=" + content +
                '}';
    }
}
