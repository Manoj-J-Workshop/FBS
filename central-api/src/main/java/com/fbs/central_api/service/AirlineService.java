package com.fbs.central_api.service;

import com.fbs.central_api.connectors.DBApiConnector;
import com.fbs.central_api.connectors.GeminiConnector;
import com.fbs.central_api.dto.airlinesRegistrationDTO;
import com.fbs.central_api.enums.AirlineStatus;
import com.fbs.central_api.enums.UserStatus;
import com.fbs.central_api.model.Airline;
import com.fbs.central_api.model.AppUser;
import com.fbs.central_api.model.GeminiResponse;
import com.fbs.central_api.utility.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AirlineService {

    Mapper mapper;
    DBApiConnector dbApiConnector;
    UserService userService;
    MailService mailService;
    GeminiConnector geminiConnector;
    @Autowired
    public AirlineService(Mapper mapper, DBApiConnector dbApiConnector, UserService userService, MailService mailService, GeminiConnector geminiConnector){
        this.mapper = mapper;
        this.dbApiConnector = dbApiConnector;
        this.userService = userService;
        this.mailService = mailService;
        this.geminiConnector = geminiConnector;
    }

    public Airline getAirlineById(UUID airlineId){
        // Need to connect DB API
        // Need DB-api connector
        return dbApiConnector.callGetAirlineByIDEndpoint(airlineId);
    }

    public Airline updateAirlineDetails(Airline airline){
        // we should call db-api to update the airline object in Airline table
        return dbApiConnector.callupdateAirlineEndpoint(airline);
    }
    /*
    This method function is to call DB API and to save airline details in airline table and airline_admin details in user table
     */

    public Airline registerAirline(airlinesRegistrationDTO airlinesRegistrationDTO){
        log.info("airlineService registration method called");
        // before calling the DB API, Lets map the details getting from dto into models
        // Ideally mapping logic should be kept in different class
        AppUser airlineAdmin = mapper.mapAirlineDetailsToAppUser(airlinesRegistrationDTO);
        //After creating airline admin object we need to call DB API to save this object in the table.
        // Means we need to connect with DB API AppUser registration endpoint
        // so create connector call
        log.info("calling DBApiConnector callCreateUserEndpoint with payload: "+ airlineAdmin.toString());
        airlineAdmin = dbApiConnector.callCreateUserEndpoint(airlineAdmin);
        log.info("airline Admin account got created in DB. Response: "+ airlineAdmin.toString());
        //Mapping airlineRegistrationDto to Airline object -> we need to write another mapper.
        Airline airline = mapper.mapAirlineDetailsDtoToAirline(airlinesRegistrationDTO,airlineAdmin);
        // Now we got the Airline object, now we need to save this into Airline table
        // So to save this airline into the airline table, we need to call DBApiConnector ->
        // internally call callCreateAirlineEndpoint
        log.info("calling DBApiConnector callCreateAirlineEndpoint with payload: "+ airline.toString());
        airline = dbApiConnector.callCreateAirlineEndpoint(airline);
        log.info("airline account got created in DB. Response: "+ airline.toString());

        //When we have created Inactive records for both Airline and AirlineAdmin
        // We need to mail to app admin that airline is trying to register into your application
        //To make this
        //We need to creating another microservice whose work is to send notification via mail
        // Now we need to mail application admin regarding airline registration request
        // So, To mail we require application admin object
        List<AppUser> SystemAdminList = userService.getAllSystemAdmins();
        //Mail all system admin
        mailService.mailSystemAdminForAirlineRegistration(SystemAdminList, airline);

        return airline;

    }

    /*
    This method is used to update the Airline status
     */
    public Airline acceptAirlineRequest(UUID airlineID){
        log.info("airlineID :"+ airlineID.toString());


        //1. get airline object by airlineID
        Airline airline = this.getAirlineById(airlineID);


        //2. update status of the airline and airlineAdmin
        airline.setStatus(AirlineStatus.ACTIVE.toString());
        airline.setUpdatedAt(LocalDateTime.now());

        //airlineAdmin
        AppUser airlineAdmin = airline.getAdmin();
        airlineAdmin.setStatus(UserStatus.ACTIVE.toString());
        airlineAdmin.setUpdatedAt(LocalDateTime.now());


        //3. Save those changes in the respective database.
             // Need to update Airline table - airline object
        airline = updateAirlineDetails(airline);
             // Need to update User table - airlineAdmin object
        airlineAdmin = userService.updateUserDetails(airlineAdmin);


        //4. Mail Airline Admin - Congratulations your request got approved.
        mailService.notifyAcceptRequestToAirlineAdmin(airline);

        return airline;

    }

    public Airline rejectAirlineRequest(UUID airlineID){
        log.info("Reject AirlineService airlineId: "+airlineID.toString());

        Airline airline = this.getAirlineById(airlineID);
        airline.setStatus(UserStatus.REJECTED.toString());
        airline = this.updateAirlineDetails(airline);

        //We need to generate rejection reasons
        String prompt = "Generate Failure Reason for the airline details: " + airline.toString();
        GeminiResponse geminiResponse = geminiConnector.callGeminiGenAIEndpoint(prompt);
        String result = geminiResponse.getCandidates().get(0).getContent().getParts().get(0).getText();

        //We need to mail this result to airline admin, as there request got canceled because of these reasons

    }
}
