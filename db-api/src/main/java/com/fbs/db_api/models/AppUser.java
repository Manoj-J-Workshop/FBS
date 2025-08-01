package com.fbs.db_api.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(nullable = false)
    String name;

    @Column(unique = true,nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    @Column(unique = true, nullable = false)
    long phoneNumber;

    @Column(nullable = false)
    boolean isVerified;

    String userType;

    String status;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
