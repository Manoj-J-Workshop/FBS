package com.fbs.db_api.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/*
This going to represent booking details
Direct flight - subFlights is empty
Connecting Flights - subFlights will has all subflights of passenger is going to cover
//Ex: sunflights - [(Delhi to Mumbai),(Mumbai to Hyderabad)]
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @ManyToOne
    Flight flight;
    @ManyToMany
    List<SubFlight> subFlights;
    @ManyToOne
    AppUser bookedBy;
    int totalAmount;
    String passengerName;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
