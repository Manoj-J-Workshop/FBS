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
@Table(name = "subflight")
public class SubFlight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @ManyToOne
    Flight flight;

    int priority;

    String sourceAirport;
    String destinationAirport;
    int boardingMinutes;

    LocalDateTime boardingTime;
    LocalDateTime departureTime;
    LocalDateTime arrivalTime;


}
