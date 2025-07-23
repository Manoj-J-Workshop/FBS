package com.fbs.central_api.dto;

import com.fbs.central_api.model.Airline;
import com.fbs.central_api.model.AppUser;
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