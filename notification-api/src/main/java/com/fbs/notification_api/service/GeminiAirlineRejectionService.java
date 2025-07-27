package com.fbs.notification_api.service;

import com.fbs.notification_api.dto.AirlineRegistrationReqDto;
import com.fbs.notification_api.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Service for generating airline rejection reasons using Gemini API
 */
@Service
@Slf4j
public class GeminiAirlineRejectionService {

    @Value("${gemini.api.key:}")
    private String geminiApiKey;

    @Value("${gemini.api.url:https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent}")
    private String geminiApiUrl;

    private final RestTemplate restTemplate;

    public GeminiAirlineRejectionService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Generate a rejection reason for airline account using Gemini API
     * @param airlineRegistrationReqDto The airline registration request data
     * @param adminReply The system admin's reply/feedback
     * @return Generated rejection reason
     */
    public String generateAirlineRejectionReason(AirlineRegistrationReqDto airlineRegistrationReqDto, String adminReply) {
        try {
            // Build the prompt for Gemini
            String prompt = buildRejectionPrompt(airlineRegistrationReqDto, adminReply);
            
            // Create Gemini request
            GeminiRequest geminiRequest = createGeminiRequest(prompt);
            
            // Call Gemini API
            GeminiResponse response = callGeminiApi(geminiRequest);
            
            // Extract and return the rejection reason
            return extractRejectionReason(response);
            
        } catch (Exception e) {
            log.error("Error generating airline rejection reason: {}", e.getMessage(), e);
            return generateFallbackRejectionReason(adminReply);
        }
    }

    /**
     * Build a comprehensive prompt for Gemini to generate rejection reason
     */
    private String buildRejectionPrompt(AirlineRegistrationReqDto airlineRegistrationReqDto, String adminReply) {
        StringBuilder prompt = new StringBuilder();
        
        prompt.append("You are a professional system administrator for a Flight Booking System. ");
        prompt.append("Generate a polite but firm rejection reason for an airline account application. ");
        prompt.append("The rejection should be professional, specific, and constructive.\n\n");
        
        prompt.append("AIRLINE APPLICATION DETAILS:\n");
        prompt.append("- Airline Name: ").append(airlineRegistrationReqDto.getAirline().getAirlineName()).append("\n");
        prompt.append("- Official Name: ").append(airlineRegistrationReqDto.getAirline().getOfficialName()).append("\n");
        prompt.append("- Website: ").append(airlineRegistrationReqDto.getAirline().getWebsite()).append("\n");
        prompt.append("- Phone: ").append(airlineRegistrationReqDto.getAirline().getPhoneNumber()).append("\n");
        prompt.append("- Employees: ").append(airlineRegistrationReqDto.getAirline().getEmployees()).append("\n");
        prompt.append("- Total Flights: ").append(airlineRegistrationReqDto.getAirline().getTotalFlights()).append("\n");
        prompt.append("- Admin Name: ").append(airlineRegistrationReqDto.getAirline().getAdmin().getName()).append("\n");
        prompt.append("- Admin Email: ").append(airlineRegistrationReqDto.getAirline().getAdmin().getEmail()).append("\n\n");
        
        prompt.append("SYSTEM ADMIN FEEDBACK:\n");
        prompt.append(adminReply).append("\n\n");
        
        prompt.append("Please generate a professional rejection email that:\n");
        prompt.append("1. Thanks them for their application\n");
        prompt.append("2. Clearly states the rejection\n");
        prompt.append("3. Provides specific reasons based on the admin feedback\n");
        prompt.append("4. Offers constructive suggestions for improvement\n");
        prompt.append("5. Encourages them to reapply after addressing the issues\n");
        prompt.append("6. Maintains a professional and respectful tone\n\n");
        
        prompt.append("Generate only the email content, no subject line needed.");
        
        return prompt.toString();
    }

    /**
     * Create Gemini API request with the given prompt
     */
    private GeminiRequest createGeminiRequest(String prompt) {
        GeminiPart part = new GeminiPart(prompt);
        GeminiContent content = new GeminiContent(Arrays.asList(part));
        return new GeminiRequest(Arrays.asList(content));
    }

    /**
     * Call Gemini API with the request
     */
    private GeminiResponse callGeminiApi(GeminiRequest request) throws Exception {
        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Build URL with API key
        String urlWithKey = geminiApiUrl + "?key=" + geminiApiKey;
        
        // Create HTTP entity
        HttpEntity<GeminiRequest> entity = new HttpEntity<>(request, headers);
        
        // Make API call
        ResponseEntity<GeminiResponse> response = restTemplate.exchange(
            urlWithKey,
            HttpMethod.POST,
            entity,
            GeminiResponse.class
        );
        
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to get response from Gemini API");
        }
    }

    /**
     * Extract rejection reason from Gemini response
     */
    private String extractRejectionReason(GeminiResponse response) {
        if (response != null && 
            response.getCandidates() != null && 
            !response.getCandidates().isEmpty() &&
            response.getCandidates().get(0).getContent() != null &&
            response.getCandidates().get(0).getContent().getParts() != null &&
            !response.getCandidates().get(0).getContent().getParts().isEmpty()) {
            
            return response.getCandidates().get(0).getContent().getParts().get(0).getText();
        }
        
        throw new RuntimeException("Invalid response format from Gemini API");
    }

    /**
     * Generate a fallback rejection reason when Gemini API fails
     */
    private String generateFallbackRejectionReason(String adminReply) {
        return String.format(
            "Dear Airline Administrator,\n\n" +
            "Thank you for your interest in joining our Flight Booking System platform.\n\n" +
            "After careful review of your application, we regret to inform you that we cannot approve your airline account at this time.\n\n" +
            "Reason for rejection:\n%s\n\n" +
            "We encourage you to address the mentioned concerns and resubmit your application. " +
            "Our team is committed to working with qualified airlines to provide the best service to our customers.\n\n" +
            "If you have any questions or need clarification, please don't hesitate to contact our support team.\n\n" +
            "Best regards,\n" +
            "Flight Booking System Administration Team",
            adminReply != null ? adminReply : "Application does not meet our current requirements."
        );
    }

    /**
     * Simple method to generate rejection reason with just admin reply
     */
    public String generateSimpleRejectionReason(String adminReply) {
        String prompt = String.format(
            "Generate a professional airline account rejection email based on this admin feedback: %s. " +
            "Make it polite, specific, and constructive. Include suggestions for improvement.",
            adminReply
        );
        
        try {
            GeminiRequest request = createGeminiRequest(prompt);
            GeminiResponse response = callGeminiApi(request);
            return extractRejectionReason(response);
        } catch (Exception e) {
            log.error("Error in simple rejection generation: {}", e.getMessage());
            return generateFallbackRejectionReason(adminReply);
        }
    }
}
