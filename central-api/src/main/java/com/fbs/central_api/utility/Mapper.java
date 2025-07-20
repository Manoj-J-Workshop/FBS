package com.fbs.central_api.utility;

import com.fbs.central_api.dto.airlinesRegistrationDTO;
import com.fbs.central_api.enums.AirlineStatus;
import com.fbs.central_api.enums.UserStatus;
import com.fbs.central_api.enums.UserType;
import com.fbs.central_api.model.Airline;
import com.fbs.central_api.model.AppUser;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Mapper {

    public AppUser mapAirlineDetailsToAppUser(airlinesRegistrationDTO airlinesDetails){
        AppUser airlineAdmin = new AppUser();
        airlineAdmin.setEmail(airlinesDetails.getEmail());
        airlineAdmin.setName(airlinesDetails.getAirlineName()+"_Admin");
        airlineAdmin.setUserType(UserType.AIRLINE_ADMIN.toString());
        airlineAdmin.setPhoneNumber(airlinesDetails.getPhoneNumber());
        airlineAdmin.setVerified(false);
        airlineAdmin.setStatus(UserStatus.INACTIVE.toString());
        airlineAdmin.setPassword(airlinesDetails.getPassword());
        airlineAdmin.setCreatedAt(LocalDateTime.now());
        airlineAdmin.setUpdatedAt(LocalDateTime.now());
        return airlineAdmin;
    }

    public Airline mapAirlineDetailsDtoToAirline(airlinesRegistrationDTO airlinesDetails, AppUser airlineAdmin){
        Airline airline = new Airline();
        airline.setAirlineName(airlinesDetails.getAirlineName());
        airline.setWebsite(airlinesDetails.getWebsite());
        airline.setOfficialName(airlinesDetails.getOfficialName());
        airline.setPhoneNumber(airlinesDetails.getPhoneNumber());
        airline.setEmployees(airlinesDetails.getEmployees());
        airline.setStatus(AirlineStatus.INACTIVE.toString());
        airline.setAdmin(airlineAdmin);
        airline.setTotalFlights(airlinesDetails.getTotalFlights());
        airline.setCreatedAt(LocalDateTime.now());
        airline.setUpdatedAt(LocalDateTime.now());
        return airline;
    }
}
