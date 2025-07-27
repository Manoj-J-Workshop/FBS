package com.fbs.central_api.controllers;

import com.fbs.central_api.dto.airlinesRegistrationDTO;
import com.fbs.central_api.model.Airline;
import com.fbs.central_api.service.AirlineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    /*
    This endpoint will triggered from the mail snet to the system admin.
    when system admin click over this by accept button - this endpoint will be triggered.
    work of this endpoint - to change the status of airline to active and airline admin status to active
     */
//    @GetMapping("/request/accept/{airlineId}")
//    public void acceptAirlineRequest(@PathVariable UUID airlineId){
//        log.info("airlineId : "+airlineId.toString());
//        // we will be calling the service layer to change the status of airline and airlineAdmin to active
//        airlineService.acceptAirlineRequest(airlineId);
//    }
    @GetMapping("/request/accept/{airlineId}")
    public ResponseEntity<String> acceptAirlineRequest(@PathVariable UUID airlineId){
        log.info("airlineId : "+airlineId.toString());
        airlineService.acceptAirlineRequest(airlineId);
        return ResponseEntity.ok("✅ Airline request has been accepted successfully!");
    }

    @GetMapping("/request/reject/{airlineId}")
    public ResponseEntity<String> rejectAirlineRequest(@PathVariable UUID airlineId){
        log.info("Reject Airline airlineId: "+airlineId.toString());
        airlineService.rejectAirlineRequest(airlineId);
        return ResponseEntity.ok("❌ Airline registration request has been rejected.");
    }
}
