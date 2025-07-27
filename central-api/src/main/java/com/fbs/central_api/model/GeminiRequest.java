package com.fbs.central_api.model;

import lombok.Data;
import java.util.List;

@Data
public class GeminiRequest {
    private List<Content> contents;
}
