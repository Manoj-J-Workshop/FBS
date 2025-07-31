package com.fbs.central_api.connectors;

import com.fbs.central_api.dto.AllUsersDto;
import com.fbs.central_api.model.Airline;
import com.fbs.central_api.model.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/*
The Purpose of this call is to connect with the DB API endpoints
 */
@Component
@Slf4j
public class DBApiConnector {

    RestTemplate restTemplate;


    @Autowired
    public DBApiConnector(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Value("${db.api.url}")
    String dbApiBaseUrl; //pick the value from application.properties by using value annotation

    public AppUser callCreateUserEndpoint(AppUser appUser){
        log.info("Inside callCreateUserEndpoint method with user object: "+ appUser.toString());
        //1. Create url you want to call
        String url = dbApiBaseUrl + "/user/create";
        //2. We want to tell the Rest method want to use and what request body we want to pass
        RequestEntity request = RequestEntity.post(url).body(appUser);
        log.info("Created request: "+ request.toString());
        //3. Hit or make the request ,on postman to do this step we click send button
        //But we here going to use rest template class
        //RestTemplate restTemplate = new RestTemplate();
        // Send button(in Postman) == Resttemplate class exchange method
        log.info("Calling dbApi create user endpoint");
        ResponseEntity<AppUser> response = restTemplate.exchange(url, HttpMethod.POST,request,AppUser.class);
        log.info("Response: "+ response.toString());
        return  response.getBody();
    }

    public Airline callCreateAirlineEndpoint(Airline airline){
        log.info("Inside callCreateAirlineEndpoint method with airline object: "+ airline.toString());
        String url = dbApiBaseUrl + "/airline/create";
        RequestEntity request = RequestEntity.post(url).body(airline);
        log.info("Created request: "+request.toString());
        //RestTemplate restTemplate = new RestTemplate();
        log.info("Calling dbApi create airline endpoint");
        ResponseEntity<Airline> response = restTemplate.exchange(url, HttpMethod.POST,request,Airline.class);
        log.info("Response: "+response.toString());
        return response.getBody();
    }

    /*
    this function will make request to db-api GetAllUsersByUserType endpoint such that we will get all the system admin
     */
    public List<AppUser> callGetAllUsersByUserType(String userType){
        String url = dbApiBaseUrl+"/user/get/"+userType;
        RequestEntity<Void> request = RequestEntity.get(URI.create(url)).build();
        ResponseEntity<AllUsersDto> response = restTemplate.exchange(url, HttpMethod.GET,request, AllUsersDto.class);
        return response.getBody().getAppUsers();
    }

    public Airline callGetAirlineByIDEndpoint(UUID airlineId){
        String url = dbApiBaseUrl+"/airline/"+ airlineId.toString();
        RequestEntity request = RequestEntity.get(url).build();
        ResponseEntity<Airline> response =restTemplate.exchange(url,HttpMethod.GET, request,Airline.class);
        return response.getBody();
    }

    public Airline callupdateAirlineEndpoint(Airline airline){
        log.info("Inside callupdateAirlineEndpoint method with airline object: "+airline.toString());
        String url = dbApiBaseUrl+"/airline/update";
        RequestEntity request = RequestEntity.put(url).body(airline);
        log.info("Updated request:"+request.toString());
        ResponseEntity<Airline> response = restTemplate.exchange(url,HttpMethod.PUT,request,Airline.class);
        return response.getBody();
    }

    public AppUser callupdateUserEndpoint(AppUser appUser){
        log.info("Inside callupdateUserEndpoint method with AppUser object: "+appUser.toString());
        String url = dbApiBaseUrl+"/user/update";
        RequestEntity request = RequestEntity.put(url).body(appUser);
        log.info("Updated request:"+request.toString());
        ResponseEntity<AppUser> response = restTemplate.exchange(url,HttpMethod.PUT,request,AppUser.class);
        return  response.getBody();
    }

    public AppUser callGetUserByEmailEndpoint(String Email){
        log.info("Inside callGetUserByEmailEndpoint method with Email: "+Email);
        String url = dbApiBaseUrl+"/user/get/"+ Email;
        RequestEntity request = RequestEntity.get(url).build();
        ResponseEntity<AppUser> response = restTemplate.exchange(url,HttpMethod.GET, request,AppUser.class);
        return response.getBody();
    }
}
