package com.fbs.notification_api.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Airline {
    UUID id;
    String airlineName;
    String website;
    String officialName;
    long phoneNumber;
    int employees;
    int totalFlights;
    AppUser admin;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
