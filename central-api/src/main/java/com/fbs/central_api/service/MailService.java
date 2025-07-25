package com.fbs.central_api.service;

import com.fbs.central_api.connectors.NotificationApiConnector;
import com.fbs.central_api.dto.AirlineRegistrationReqDto;
import com.fbs.central_api.model.Airline;
import com.fbs.central_api.model.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MailService {

    NotificationApiConnector notificationApiConnector;

    @Autowired
    public MailService(NotificationApiConnector notificationApiConnector){
        this.notificationApiConnector = notificationApiConnector;
    }

    /*
    This function is responsible for sending mail to all system admin regarding airline registration
     */
    public void mailSystemAdminForAirlineRegistration(List<AppUser> systemAdmin, Airline airline){
        //Loop over all the system admin and mail one-by-one
        for(AppUser systemAdmin1: systemAdmin){
            // We need to call Notification API, one-by-one for all the system Admin
            // So, to call notification API from central API, we require - Notification API connector call
            AirlineRegistrationReqDto airlineRegistrationReqDto = new AirlineRegistrationReqDto();
            airlineRegistrationReqDto.setAirline(airline);
            airlineRegistrationReqDto.setAppAdmin(systemAdmin1);
            notificationApiConnector.notifySystemAdminForAirlineRegistration(airlineRegistrationReqDto);
        }
    }
}
