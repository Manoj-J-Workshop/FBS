package com.fbs.central_api.connectors;

import com.fbs.central_api.dto.AirlineRegistrationReqDto;
import com.fbs.central_api.dto.AirlineRejectDto;
import com.fbs.central_api.model.Airline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NotificationApiConnector {

    RestTemplate restTemplate;


    @Autowired
    public NotificationApiConnector(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Value("${notification.api.url}")
    String notificationBaseUrl;

    public void notifySystemAdminForAirlineRegistration(AirlineRegistrationReqDto airlineRegistrationReqDto){
        String url = notificationBaseUrl + "/appadmin/airline-registration";
        RequestEntity request = RequestEntity.put(url).body(airlineRegistrationReqDto);
        restTemplate.exchange(url, HttpMethod.PUT,request, Object.class);
    }

    public void notifyAcceptRequestToAirlineAdmin(Airline airline){
        String url = notificationBaseUrl + "/airline/admin/accept-request";
        RequestEntity<Object> request = RequestEntity.put(url).body(airline);
        restTemplate.exchange(url,HttpMethod.PUT,request, Object.class);
    }

    public void notifyRejectRequestToAirlineAdmin(AirlineRejectDto airlineRejectDto){
        String url = notificationBaseUrl + "/airline/admin/reject-request";
        RequestEntity<Object> request = RequestEntity.put(url).body(airlineRejectDto);
        restTemplate.exchange(url,HttpMethod.PUT,request, Object.class);
    }
}
