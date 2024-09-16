package com.TransportationService.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
public class  RideRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "pickup_location")
    private String pickupLocation;

    @Column(name = "pickup_name")
    private String pickupName;

    @Column(name = "drop_location")
    private String dropLocation;

    @Column(name = "drop_name")
    private String dropName;

    @Column(name = "distance")
    private String distance;

    @Column(name = "duration")
    private String duration;

    @Column(name = "fare")
    private int fare;

    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "seating_capacity")
    private SeatingCapacity seatingCapacity;

    @Column(name = "booking_status")
    private BookingStatus bookingStatus;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private Date createdAt;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "ride_id")
    private Ride ride = null;
}
