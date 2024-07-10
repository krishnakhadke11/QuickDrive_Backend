package com.TransportationService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private int rating=0;

    @Column(name = "fare")
    private int fare=0;

    @Column(name = "distance")
    private String distance;

    @Column(name = "duration")
    private String duration;

    @Column(name = "booking_status")
    private boolean booking_status=false; //true: Ride is booked, false: Ride is not booked

    @OneToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "cab_id",nullable = true)
    private Cab cab;

    @OneToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "driver_id")
    private Driver driver;

//    @OneToOne(mappedBy = "ride", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Payment payment;
}
