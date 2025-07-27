package com.fbs.db_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Root model class representing the main request structure
 */
public class RequestRoot {
    
    @JsonProperty("contents")
    private List<Content> contents;
    
    public RequestRoot() {}
    
    public RequestRoot(List<Content> contents) {
        this.contents = contents;
    }
    
    public List<Content> getContents() {
        return contents;
    }
    
    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
    
    @Override
    public String toString() {
        return "RequestRoot{" +
                "contents=" + contents +
                '}';
    }
}
