package com.fbs.central_api.controllers;

import com.fbs.central_api.dto.airlinesRegistrationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/v1/central/airline")
public class AirlineController {

    @PostMapping("/register")
    public ResponseEntity registerAirline(@RequestBody airlinesRegistrationDTO airlinesRegDTo){
        // Airline details --> need to catch json in airlinesDetailsDTO
        // from here call airlineService for further processing
        log.info("airlineRegistration method got called with the request body: "+ airlinesRegDTo.toString());
        log.info("calling airlineService registerAirline method");
    }
}
