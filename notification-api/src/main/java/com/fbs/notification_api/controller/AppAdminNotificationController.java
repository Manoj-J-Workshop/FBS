package com.fbs.notification_api.controller;

import com.fbs.notification_api.dto.AirlineRegistrationReqDto;
import com.fbs.notification_api.service.AppAdminNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
This controller created to send the mails/notifications to App Admin
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/notify/appadmin")
public class AppAdminNotificationController {

    AppAdminNotificationService appAdminNotificationService;

    @Autowired
    public AppAdminNotificationController(AppAdminNotificationService appAdminNotificationService){
        this.appAdminNotificationService = appAdminNotificationService;
    }

    /*
    This method work is to send the registration request email of a airline to application admin
     */

    @PutMapping("/airline-registration")
    public void airlineRegistrationRequestNotification(@RequestBody AirlineRegistrationReqDto airlineRegistrationReqDto){
        // want Admin details, airline details to notify to App admin
        log.info("Inside airlineRegistrationRequestNotification with payload: "+ airlineRegistrationReqDto.toString());
        //From here we need to call AppAdminNotificationService
        appAdminNotificationService.sendAirlineRegistrationReqNotification(airlineRegistrationReqDto);
    }
}
