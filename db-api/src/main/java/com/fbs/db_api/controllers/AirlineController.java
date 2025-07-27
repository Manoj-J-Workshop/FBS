package com.fbs.db_api.controllers;

import com.fbs.db_api.models.Airline;
import com.fbs.db_api.models.AppUser;
import com.fbs.db_api.repositories.AirlineRepo;
import com.fbs.db_api.repositories.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/db/airline")
public class AirlineController {

    AirlineRepo airlineRepo;

    @Autowired
    public AirlineController(AirlineRepo airlineRepo){
        this.airlineRepo = airlineRepo;
    }

    @PostMapping("/create")
    public ResponseEntity CreateAirline(@RequestBody Airline airline){
        Airline airlineResponse = airlineRepo.save(airline);
        return new ResponseEntity(airlineResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{airlineId}")
    public ResponseEntity getAirlineById(@PathVariable UUID airlineId){
        Airline airline = airlineRepo.findById(airlineId).orElse(null);
        return new ResponseEntity<>(airline,HttpStatus.OK);
    }

    /*
    Endpoint to update the airline table based on airline object
     */
    @PutMapping("/update")
    public ResponseEntity updateAirline(@RequestBody Airline airline){
        airlineRepo.save(airline);
        return new ResponseEntity<>(airline,HttpStatus.OK);
    }

}
