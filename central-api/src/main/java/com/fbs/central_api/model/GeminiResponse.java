package com.fbs.central_api.model;

import lombok.Data;
import java.util.List;
@Data
public class GeminiResponse {
    private List<ResponseCandidate> candidates;
    private String modelVersion;
    private String responseId;
}