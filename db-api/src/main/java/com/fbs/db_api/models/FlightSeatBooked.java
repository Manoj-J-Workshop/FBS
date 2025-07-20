package com.fbs.db_api.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/*
This booking table we are strictly going to use for non connecting flights
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "flightSeatBooked")
public class FlightSeatBooked extends SeatBooked{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @ManyToOne
    Flight flight;

    @ManyToOne
    AppUser bookedBy;

    @OneToOne
    Booking bookingId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
