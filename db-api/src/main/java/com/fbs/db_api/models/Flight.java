package com.fbs.db_api.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @OneToOne
    Airline airline;

    @OneToOne
    Aircraft aircraft;

    @Column(nullable = false)
    String srcAirport;

    @Column(nullable = false)
    String destAirport;

    @Column(nullable = false)
    String flightType;  // Improvements: add under enum of INTERNATIONAL, DOMESTIC, EMERGENCY

    int totalTime;

    LocalDateTime boardingTime; // when Passengers need to sit in aircraft
    LocalDateTime departureTime; // when aircraft takeoff
    LocalDateTime arrivalTime; // when aircraft going to land

    boolean IsConnecting;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
