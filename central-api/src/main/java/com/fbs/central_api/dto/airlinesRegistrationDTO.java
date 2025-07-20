package com.fbs.central_api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class airlinesRegistrationDTO {
    String airlineName;
    String website;
    String officialName;
    long phoneNumber;  // Improve: numbers should be link - +91 7657xxxxxx687, (106) 678665xxx
    int employees;
    int totalFlights;
    String email;
    String password;
}
