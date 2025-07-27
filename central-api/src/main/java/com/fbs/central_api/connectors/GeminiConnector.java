package com.fbs.central_api.connectors;

import com.fbs.central_api.model.Content;
import com.fbs.central_api.model.GeminiRequest;
import com.fbs.central_api.model.GeminiResponse;
import com.fbs.central_api.model.Part;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;



@Component
public class GeminiConnector {

    @Value("${gemini.genai.url}")
    String geminiAiUrl;

    @Value("${gemini.token}")
    String token;

    private final String geminiTokenHeader = "X-goog-api-key";


    private final RestTemplate restTemplate;

    public GeminiConnector(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public GeminiResponse callGeminiGenAIEndpoint(String prompt){
        // Creation of the request body
        // 1. Create Part (like RequestPart)
        Part part = new Part();
        part.setText(prompt);

        List<Part> parts = new ArrayList<>();
        parts.add(part);

        // 2. Wrap it in Content
        Content content = new Content();
        content.setParts(parts);

        List<Content> contents = new ArrayList<>();
        contents.add(content);

        // 3. Set contents in request body
        GeminiRequest geminiApiReqBody = new GeminiRequest();
        geminiApiReqBody.setContents(contents);

        // 4. Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(geminiTokenHeader, token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 5. Send POST request
        HttpEntity<GeminiRequest> httpEntity = new HttpEntity<>(geminiApiReqBody, headers);
        ResponseEntity<GeminiResponse> response = restTemplate.exchange(
                geminiAiUrl, HttpMethod.POST, httpEntity, GeminiResponse.class
        );

        return response.getBody();

    }
}
