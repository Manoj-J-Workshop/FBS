package com.fbs.notification_api.dto;

import com.fbs.notification_api.model.Airline;
import com.fbs.notification_api.model.AppUser;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AirlineRegistrationReqDto {
    AppUser appAdmin;
    Airline airline;

}
