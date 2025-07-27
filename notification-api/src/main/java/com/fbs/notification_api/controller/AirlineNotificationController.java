package com.fbs.notification_api.controller;

import com.fbs.notification_api.dto.AirlineRegistrationReqDto;
import com.fbs.notification_api.dto.AirlineRejectDto;
import com.fbs.notification_api.model.Airline;
import com.fbs.notification_api.service.AirlineNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/notify/airline")
public class AirlineNotificationController {

    AirlineNotificationService airlineNotificationService;

    @Autowired
    public AirlineNotificationController(AirlineNotificationService airlineNotificationService){
        this.airlineNotificationService = airlineNotificationService;

    }

    @PutMapping("/admin/accept-request")
    public void AirlineAdminAcceptNotification(@RequestBody Airline airline){
        //Service
        airlineNotificationService.airlineacceptNotification(airline);

    }

    @PutMapping("/admin/reject-request")
    public void AirlineAdminRejectNotification(@RequestBody AirlineRejectDto airlineRejectDto){
        //Service
        airlineNotificationService.airlineRejectNotification(airlineRejectDto);

    }
}
