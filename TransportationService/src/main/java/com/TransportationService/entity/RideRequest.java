package com.TransportationService.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class RideRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String pickupLocation;

    private String pickupName;

    private String dropLocation;

    private String dropName;

    private String distance;

    private String duration;

    private int fare;

    private PaymentType paymentType;

    private SeatingCapacity seatingCapacity;

    private BookingStatus bookingStatus;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private Date createdAt;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "ride_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Ride ride = null;
}
