package com.fbs.central_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Model class representing content with parts
 */
public class Content {
    
    @JsonProperty("parts")
    private List<Part> parts;
    
    public Content() {}
    
    public Content(List<Part> parts) {
        this.parts = parts;
    }
    
    public List<Part> getParts() {
        return parts;
    }
    
    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
    
    @Override
    public String toString() {
        return "Content{" +
                "parts=" + parts +
                '}';
    }
}
