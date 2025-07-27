package com.fbs.notification_api.controller;

import com.fbs.notification_api.dto.AirlineRegistrationReqDto;
import com.fbs.notification_api.service.GeminiAirlineRejectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling airline rejection with Gemini AI integration
 */
@RestController
@RequestMapping("/api/v1/airline/rejection")
@Slf4j
public class AirlineRejectionController {

    private final GeminiAirlineRejectionService geminiAirlineRejectionService;

    @Autowired
    public AirlineRejectionController(GeminiAirlineRejectionService geminiAirlineRejectionService) {
        this.geminiAirlineRejectionService = geminiAirlineRejectionService;
    }

    /**
     * Generate rejection reason for airline account using Gemini AI
     * @param airlineRegistrationReqDto Airline registration request data
     * @param adminReply System admin's feedback/reply
     * @return Generated rejection reason
     */
    @PostMapping("/generate-reason")
    public ResponseEntity<String> generateRejectionReason(
            @RequestBody AirlineRegistrationReqDto airlineRegistrationReqDto,
            @RequestParam(required = false) String adminReply) {
        
        try {
            log.info("Generating rejection reason for airline: {}", 
                    airlineRegistrationReqDto.getAirline().getAirlineName());
            
            String rejectionReason = geminiAirlineRejectionService
                    .generateAirlineRejectionReason(airlineRegistrationReqDto, adminReply);
            
            return ResponseEntity.ok(rejectionReason);
            
        } catch (Exception e) {
            log.error("Error generating rejection reason: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Error generating rejection reason: " + e.getMessage());
        }
    }

    /**
     * Simple endpoint to generate rejection reason with just admin reply
     * @param adminReply System admin's feedback
     * @return Generated rejection reason
     */
    @PostMapping("/generate-simple")
    public ResponseEntity<String> generateSimpleRejectionReason(@RequestParam String adminReply) {
        try {
            log.info("Generating simple rejection reason");
            
            String rejectionReason = geminiAirlineRejectionService
                    .generateSimpleRejectionReason(adminReply);
            
            return ResponseEntity.ok(rejectionReason);
            
        } catch (Exception e) {
            log.error("Error generating simple rejection reason: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Error generating rejection reason: " + e.getMessage());
        }
    }
}
