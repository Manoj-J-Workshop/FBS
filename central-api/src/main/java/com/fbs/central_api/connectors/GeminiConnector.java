package com.fbs.central_api.connectors;

import org.springframework.beans.factory.annotation.Value;

public class GeminiConnector {

    @Value("${gemini.genai.url}")
    String geminiAiUrl;

    @Value("${gemini.token}")
    String token;

    public Object callGeminiGenAIEndpoint(){

    }
}
