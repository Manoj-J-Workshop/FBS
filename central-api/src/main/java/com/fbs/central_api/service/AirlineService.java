package com.fbs.central_api.service;

import com.fbs.central_api.connectors.DBApiConnector;
import com.fbs.central_api.dto.airlinesRegistrationDTO;
import com.fbs.central_api.model.Airline;
import com.fbs.central_api.model.AppUser;
import com.fbs.central_api.utility.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AirlineService {

    Mapper mapper;
    DBApiConnector dbApiConnector;
    @Autowired
    public AirlineService(Mapper mapper, DBApiConnector dbApiConnector){
        this.mapper = mapper;
        this.dbApiConnector = dbApiConnector;
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



    }
}
