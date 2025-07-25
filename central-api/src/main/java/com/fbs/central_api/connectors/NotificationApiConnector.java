package com.fbs.central_api.connectors;

import com.fbs.central_api.dto.AirlineRegistrationReqDto;
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
}
