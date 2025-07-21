package com.fbs.central_api.controllers;

import com.fbs.central_api.dto.airlinesRegistrationDTO;
import com.fbs.central_api.model.Airline;
import com.fbs.central_api.service.AirlineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/v1/central/airline")
public class AirlineController {

    AirlineService airlineService;

    @Autowired
    public AirlineController(AirlineService airlineService){
        this.airlineService = airlineService;
    }

    @PostMapping("/register")
    public ResponseEntity registerAirline(@RequestBody airlinesRegistrationDTO airlinesRegDTo){
        // Airline details --> need to catch json in airlinesDetailsDTO
        // from here call airlineService for further processing
        log.info("airlineRegistration method got called with the request body: "+ airlinesRegDTo.toString());
        log.info("calling airlineService registerAirline method");
        // call airline service register Airline method
        Airline airline = airlineService.registerAirline(airlinesRegDTo);
        return new ResponseEntity(airline, HttpStatus.CREATED);
    }
}
