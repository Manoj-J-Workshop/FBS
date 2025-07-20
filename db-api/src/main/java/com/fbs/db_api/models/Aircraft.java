package com.fbs.db_api.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "aircraft")
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(unique = true, nullable = false)
    String AircraftName;

    @Column(nullable = false)
    int modelNumber;

    @Column(nullable = false)
    String manufacturer;

    String modelName;

    @Column(nullable = false)
    int totalFlights;

    LocalDate buildDate;

    @ManyToOne
    Airline airline;

    int capacity;

}
