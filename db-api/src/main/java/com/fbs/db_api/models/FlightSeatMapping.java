package com.fbs.db_api.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/*
This flight seat mapping model will be used for non-connecting flights
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "flightSeatMapping")
public class FlightSeatMapping extends SeatMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;


    @ManyToOne
    Flight flight;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
