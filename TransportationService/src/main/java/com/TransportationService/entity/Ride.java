package com.TransportationService.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ride")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "pickup_location", nullable = false)
    private String pickupLocation;

    @Column(name = "drop_location", nullable = false)
    private String dropLocation;

    @Column(name = "rating")
    private int rating;

    @Column(name = "fare")
    private int fare;

    @Column(name = "distance")
    private double distance;

    @Column(name = "booking_status")
    private boolean booking_status; //true: Ride is booked, false: Ride is not booked

    @OneToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "cab_id",referencedColumnName = "id",nullable = true)
    private Cab cab;

    @OneToOne(mappedBy = "ride", cascade = CascadeType.ALL)
    private Payment payment;
}
