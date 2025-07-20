package com.fbs.db_api.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "airlines")
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(nullable = false)
    String airlineName;

    @Column(unique = true, nullable = false)
    String website;

    @Column(unique = true,nullable = false)
    String officialName;

    @Column(unique = true, nullable = false)
    long phoneNumber;  // Improve: numbers should be link - +91 7657xxxxxx687, (106) 678665xxx

    int employees;

    int totalFlights;

    @OneToOne
    AppUser admin;

    String status;
}
