package com.fbs.central_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class representing a part with text content
 */
public class Part {
    
    @JsonProperty("text")
    private String text;
    
    public Part() {}
    
    public Part(String text) {
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
        return "Part{" +
                "text='" + text + '\'' +
                '}';
    }
}
